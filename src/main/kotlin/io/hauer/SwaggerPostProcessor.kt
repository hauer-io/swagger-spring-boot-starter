package io.hauer

import io.hauer.SwaggerAutoConfiguration.Companion.CONFIG_PREFIX
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.core.env.Environment

interface SwaggerPostProcessor : BeanFactoryPostProcessor

class SwaggerPostProcessorImpl (private val environment: Environment, private val producer: SwaggerProducer) : SwaggerPostProcessor{
    override fun postProcessBeanFactory(factory: ConfigurableListableBeanFactory) {
        environment.bindSwaggerConfig() //
                .ifBound { properties ->
                    producer.generate(properties.default, properties.groups) //
                            .forEach {
                                factory.registerSingleton(it.groupName, it)
                            }
                }
    }

    private fun Environment.bindSwaggerConfig() = Binder.get(this).bind(CONFIG_PREFIX, SwaggerConfig::class.java)!!
}