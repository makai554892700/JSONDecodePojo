package com.mayousheng.www.minify.ext

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer

class MinifyExt {
    Map<String, Closure<MinifyConfig>> configMap = [:]

    NamedDomainObjectContainer<MinifyConfig> variantConfig

    void variantConfig(Action<? super NamedDomainObjectContainer<MinifyConfig>> action) {
        action.execute(variantConfig)
    }
}