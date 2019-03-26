package io.hauer

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.core.env.Environment


/**
 * @author Jan Hauer
 * @since 1.0
 */
class SwaggerPostProcessorImpl(private val environment: Environment, private val producer: SwaggerProducer, private val transformer: List<SwaggerTransformer>) : SwaggerPostProcessor {
    override fun postProcessBeanFactory(factory: ConfigurableListableBeanFactory) {
        val properties = environment.getSwaggerConfig()
        producer.generate(properties.default, properties.groups, transformer).forEach {
            factory.registerSingleton(it.groupName, it)
        }
    }

    private fun Environment.getSwaggerConfig() = Binder.get(this)
            .bind(SwaggerAutoConfiguration.CONFIG_PREFIX, SwaggerConfig::class.java)
            .orElse(SwaggerConfig())
}