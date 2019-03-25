package io.hauer

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
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
    @ConditionalOnMissingBean
    fun swaggerPostProcessor(environment: Environment, producer: SwaggerProducer, transformer: List<SwaggerTransformer>): SwaggerPostProcessor = SwaggerPostProcessorImpl(environment, producer, transformer)

    @Bean
    @ConditionalOnMissingBean
    fun swaggerProducer(): SwaggerProducer = object : SwaggerProducer{}

    companion object {
        const val CONFIG_PREFIX = "io.hauer.swagger"
    }
}
