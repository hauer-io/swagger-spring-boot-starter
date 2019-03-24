package io.hauer

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.web.WebApplicationInitializer
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * @author Jan Hauer
 * @since 1.0
 */

@ConditionalOnClass(WebApplicationInitializer::class)
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfig::class)
class SwaggerAutoConfiguration {

    @Bean
    fun swaggerBeanFactoryPostProcessor(environment: Environment, producer: SwaggerProducer) = //
            BeanFactoryPostProcessor { beanFactory ->
                environment.bindSwaggerConfig() //
                        .ifBound { properties ->
                            producer.generate(properties.default, properties.groups) //
                                    .forEach {
                                        beanFactory.registerSingleton(it.groupName, it)
                                    }
                        }
            }

    private fun Environment.bindSwaggerConfig() = Binder.get(this).bind(CONFIG_PREFIX, SwaggerConfig::class.java)!!

    @Bean
    fun swaggerProducer() = SwaggerProducerImpl()

    companion object {
        const val CONFIG_PREFIX = "io.hauer.swagger"
    }
}
