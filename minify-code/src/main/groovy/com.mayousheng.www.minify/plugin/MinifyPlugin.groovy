package com.mayousheng.www.minify.plugin

import com.mayousheng.www.minify.ext.MinifyExt
import com.mayousheng.www.minify.ext.MinifyConfig
import com.mayousheng.www.minify.task.MinifyTask
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class MinifyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def android = project.extensions.getByType(AppExtension)
        if (!android) {
            throw IllegalArgumentException("must apply this plugin after 'com.android.application'")
        }
        def generateJunkCodeExt = project.extensions.create("minify", MinifyExt)
        generateJunkCodeExt.variantConfig = project.container(MinifyConfig.class, new MinifyConfigFactory())

        android.applicationVariants.all { variant ->
            def variantName = variant.name
            def junkCodeConfig = generateJunkCodeExt.variantConfig.findByName(variantName)
            if (junkCodeConfig) {
                createGenerateJunkCodeTask(project, android, variant, junkCodeConfig)
            }
        }

        project.afterEvaluate {
            android.applicationVariants.all { variant ->
                def variantName = variant.name
                def generateJunkCodeTaskName = "generate${variantName.capitalize()}MinifyCode"
                def generateJunkCodeTask = project.tasks.findByName(generateJunkCodeTaskName)
                if (generateJunkCodeTask) {
                    //已经用variantConfig方式配置过了
                    return
                }
                def closure = generateJunkCodeExt.configMap[variantName]
                if (closure) {
                    def junkCodeConfig = new MinifyConfig()
                    closure.delegate = junkCodeConfig
                    closure.resolveStrategy = Closure.DELEGATE_FIRST
                    closure.call()
                    println("MinifyCode: configMap配置方式已过时，请使用variantConfig配置方式")
                    createGenerateJunkCodeTask(project, android, variant, junkCodeConfig)
                }
            }
        }
    }

    private def createGenerateJunkCodeTask = { project, android, variant, junkCodeConfig ->
        def variantName = variant.name
        def generateJunkCodeTaskName = "generate${variantName.capitalize()}MinifyCode"
        def dir = new File(project.buildDir, "generated/source/junk/$variantName")
        def resDir = new File(dir, "res")
        def javaDir = new File(dir, "java")
        def manifestFile = new File(dir, "AndroidManifest.xml")
        //从main/AndroidManifest.xml找到package name
        def mainManifestFile = android.sourceSets.findByName("main").manifest.srcFile
        def parser = new XmlParser()
        def node = parser.parse(mainManifestFile)
        def packageName = node.attribute("package")
        def generateJunkCodeTask = project.task(generateJunkCodeTaskName, type: MinifyTask) {
            config = junkCodeConfig
            manifestPackageName = packageName
            outDir = dir
        }
        //将自动生成的AndroidManifest.xml加入到一个未被占用的manifest位置(如果都占用了就不合并了，通常较少出现全被占用情况)
        for (int i = variant.sourceSets.size() - 1; i >= 0; i--) {
            def sourceSet = variant.sourceSets[i]
            if (!sourceSet.manifestFile.exists()) {
                android.sourceSets."${sourceSet.name}".manifest.srcFile(manifestFile.absolutePath)
                def processMainManifestTask = project.tasks.findByName("process${variantName.capitalize()}MainManifest")
                if (processMainManifestTask) {
                    processMainManifestTask.dependsOn(generateJunkCodeTask)
                }
                break
            }
        }
        if (variant.respondsTo("registerGeneratedResFolders")) {
            generateJunkCodeTask.ext.generatedResFolders = project
                    .files(resDir)
                    .builtBy(generateJunkCodeTask)
            variant.registerGeneratedResFolders(generateJunkCodeTask.generatedResFolders)
            if (variant.hasProperty("mergeResourcesProvider")) {
                variant.mergeResourcesProvider.configure { dependsOn(generateJunkCodeTask) }
            } else {
                //noinspection GrDeprecatedAPIUsage
                variant.mergeResources.dependsOn(generateJunkCodeTask)
            }
        } else {
            //noinspection GrDeprecatedAPIUsage
            variant.registerResGeneratingTask(generateJunkCodeTask, resDir)
        }
        variant.registerJavaGeneratingTask(generateJunkCodeTask, javaDir)
    }
}