package com.mayousheng.www.minify.plugin

import com.mayousheng.www.minify.ext.MinifyConfig
import org.gradle.api.NamedDomainObjectFactory

class MinifyConfigFactory implements NamedDomainObjectFactory<MinifyConfig> {

    @Override
    MinifyConfig create(String name) {
        return new MinifyConfig(name)
    }
}