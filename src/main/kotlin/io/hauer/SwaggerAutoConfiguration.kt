package io.hauer

import io.hauer.SwaggerBaseConfig.Companion.defaultBase
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.web.WebApplicationInitializer
import springfox.documentation.swagger2.annotations.EnableSwagger2
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * @author Jan Hauer
 * @since 1.0
 */

@ConditionalOnClass(WebApplicationInitializer::class)
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfig::class)
class SwaggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun swaggerBeanFactory(environment: Environment,
                           producer: SwaggerProducer,
                           base: SwaggerBaseConfig) = object : SwaggerBeanFactory {
        override fun postProcessBeanFactory(factory: ConfigurableListableBeanFactory) {
            val swaggerConfig = environment.getSwaggerConfig()
            producer.create(base, swaggerConfig.default, swaggerConfig.groups).forEach {
                factory.registerSingleton("${it.groupName}SwaggerDocket", it)
            }
        }
    }

    @Bean
    @ConditionalOnMissingBean
    fun swaggerProducer(): SwaggerProducer = object : SwaggerProducer {}

    @Bean
    @ConditionalOnMissingBean
    fun swaggerBaseConfig(applicationContext: ApplicationContext) = object : SwaggerBaseConfig {
        override val base = defaultBase.apply {
            this["basePackage"] = applicationContext.getBeansWithAnnotation(SpringBootApplication::class.java).values
                    .map { c -> c.javaClass.`package`.name }
                    .ifEmpty<List<String>, List<String>> { listOf("") }[0]
        }
    }
}
