package io.hauer.spring

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.web.WebApplicationInitializer
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

/**
 * @author Jan Hauer
 * @since 1.0
 */

@ConditionalOnClass(WebApplicationInitializer::class)
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfigurationProperties::class)
class SwaggerAutoConfiguration {

    @Bean
    fun swaggerBeanFactoryPostProcessor(environment: Environment) = BeanFactoryPostProcessor { beanFactory ->
        Binder.get(environment) //
                .bind(CONFIG_PREFIX, SwaggerConfigurationProperties::class.java) //
                .ifBound { properties ->
                    beanFactory.registerSingleton("SwaggerDocket", docket(properties.basePackage, properties.regex))
                }
    }
}

const val CONFIG_PREFIX = "io.hauer.spring.swagger"

private fun docket(basePackage: String, pathRegex: String) = Docket(DocumentationType.SWAGGER_2) //
        .select() //
        .apis(RequestHandlerSelectors.basePackage(basePackage)) //
        .paths(PathSelectors.regex(pathRegex)) //
        .build()
