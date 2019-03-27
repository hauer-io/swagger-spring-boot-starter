package io.hauer

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.core.env.Environment

interface SwaggerBeanFactory : BeanFactoryPostProcessor {
    fun Environment.getSwaggerConfig() = Binder.get(this)
            .bind("swagger", SwaggerConfig::class.java)
            .orElse(SwaggerConfig())
}
