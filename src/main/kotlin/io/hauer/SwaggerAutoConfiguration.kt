package io.hauer

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
    fun swaggerPostProcessor(environment: Environment, producer: SwaggerProducer, transformer: List<SwaggerTransformer>, fallbacks: SwaggerConfigFallbacks): SwaggerPostProcessor = SwaggerPostProcessorImpl(environment, producer, transformer, fallbacks)

    @Bean
    @ConditionalOnMissingBean
    fun swaggerProducer(): SwaggerProducer = object : SwaggerProducer {}

    companion object {
        const val CONFIG_PREFIX = "io.hauer.swagger"
    }

    @Bean
    @ConditionalOnMissingBean
    fun swaggerConfigFallbacks(applicationContext: ApplicationContext) = SwaggerConfigFallbacks().also {
        val basePackage = applicationContext.getBeansWithAnnotation(SpringBootApplication::class.java).values
                .map { c -> c.javaClass.`package`.name }
                .ifEmpty { listOf("") }[0]
        it.setFallback("basePackage", basePackage)
        it.setFallback("regex", ".*")
        it.setFallback("title", "Api Documentation")
        it.setFallback("description", "Api Documentation for controller in package \"$basePackage\"")
    }
}
