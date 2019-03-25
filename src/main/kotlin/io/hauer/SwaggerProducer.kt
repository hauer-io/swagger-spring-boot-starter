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
    fun generate(default: SwaggerConfig.Info, groups: Map<String, SwaggerConfig.Info>, transformer: List<SwaggerTransformer>): List<Docket> {
        return if (groups.isEmpty()) {
            listOf(createDocket("default", default, default,transformer))
        } else {
            groups.map { entry -> createDocket(entry.key, entry.value, default, transformer) }
        }
    }

    fun createDocket(name: String, group: SwaggerConfig.Info, default: SwaggerConfig.Info, transformer: List<SwaggerTransformer>) = //
            Docket(DocumentationType.SWAGGER_2) //
                    .groupName(name)
                    .consumes(group.consumes.ifEmpty { default.consumes })
                    .produces(group.produces.ifEmpty { default.produces })
                    .apiInfo(ApiInfo(group.title ?: default.title ?: "",
                            group.description ?: default.description ?: "",
                            group.version ?: default.version ?: "",
                            group.termsOfServiceUrl ?: default.termsOfServiceUrl ?: "",
                            Contact(group.contactName ?: default.contactName ?: "",
                                    group.contactUrl ?: default.contactUrl ?: "",
                                    group.contactEmail ?: default.contactEmail ?: ""),
                            group.license ?: default.license ?: "",
                            group.licenseUrl ?: default.licenseUrl ?: "",
                            group.vendorExtensions.ifEmpty { default.vendorExtensions }))
                    .select() //
                    .apis(RequestHandlerSelectors.basePackage(group.basePackage ?: default.basePackage ?: "")) //
                    .paths(PathSelectors.regex(group.regex ?: default.regex ?: ".*")) //
                    .build()!!
                    .apply(transformer)

    fun Docket.apply(transformer: List<SwaggerTransformer>) = transformer.fold(this) { d, t -> t.transform(d) }
}