package io.hauer.spring

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.web.WebApplicationInitializer
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

/**
 * @author Jan Hauer
 * @since 1.0
 */

@ConditionalOnClass(WebApplicationInitializer::class)
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfigurationProperties::class)
class SwaggerAutoConfiguration {

    @Bean
    fun swaggerBeanFactoryPostProcessor(environment: Environment, swaggerDocketFactory: SwaggerDocketFactory) = BeanFactoryPostProcessor { beanFactory ->
        Binder.get(environment) //
                .bind(CONFIG_PREFIX, SwaggerConfigurationProperties::class.java) //
                .ifBound { properties ->
                    properties.docket
                            ?: Collections.singletonMap("default", SwaggerConfigurationProperties.SwaggerDocketInformation(properties.default))
                                    .forEach { name, info ->
                                        beanFactory.registerSingleton("${name}SwaggerDocket", swaggerDocketFactory.create(name, info, properties.default))
                                    }
                }
    }

    @Bean
    fun docketFactory() = SwaggerDocketFactory()

    companion object {
        const val CONFIG_PREFIX = "io.hauer.spring.swagger"
    }
}
