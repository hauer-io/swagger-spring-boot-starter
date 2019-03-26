package io.hauer

import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

/**
 * @author Jan Hauer
 * @since 1.0
 */
interface SwaggerProducer {
    fun generate(default: SwaggerConfig.Info,
                 groups: Map<String, SwaggerConfig.Info>,
                 transformer: List<SwaggerTransformer>,
                 fallbacks: SwaggerConfigFallbacks): List<Docket> {
        return if (groups.isEmpty()) {
            listOf(createDocket("default", default, default,transformer,fallbacks))
        } else {
            groups.map { entry -> createDocket(entry.key, entry.value, default, transformer, fallbacks) }
        }
    }

    fun createDocket(name: String, group: SwaggerConfig.Info, default: SwaggerConfig.Info, transformer: List<SwaggerTransformer>, fallbacks: SwaggerConfigFallbacks) = //
            Docket(DocumentationType.SWAGGER_2) //
                    .groupName(name)
                    .consumes(group.consumes.ifEmpty { default.consumes })
                    .produces(group.produces.ifEmpty { default.produces })
                    .apiInfo(ApiInfo(group.title ?: default.title ?: fallbacks.getFallback("title"),
                            group.description ?: default.description ?: fallbacks.getFallback("description"),
                            group.version ?: default.version ?: fallbacks.getFallback("version"),
                            group.termsOfServiceUrl ?: default.termsOfServiceUrl ?: fallbacks.getFallback("termsOfServiceUrl"),
                            Contact(group.contactName ?: default.contactName ?: fallbacks.getFallback("contactName"),
                                    group.contactUrl ?: default.contactUrl ?: fallbacks.getFallback("contactUrl"),
                                    group.contactEmail ?: default.contactEmail ?: fallbacks.getFallback("contactEmail")),
                            group.license ?: default.license ?: fallbacks.getFallback("license"),
                            group.licenseUrl ?: default.licenseUrl ?: fallbacks.getFallback("licenseUrl"),
                            group.vendorExtensions.ifEmpty { default.vendorExtensions }))
                    .select() //
                    .apis(RequestHandlerSelectors.basePackage(group.basePackage ?: default.basePackage ?: fallbacks.getFallback("basePackage"))) //
                    .paths(PathSelectors.regex(group.regex ?: default.regex ?: ".*")) //
                    .build()!!
                    .apply(transformer)

    fun Docket.apply(transformer: List<SwaggerTransformer>) = transformer.fold(this) { d, t -> t.transform(d) }
}