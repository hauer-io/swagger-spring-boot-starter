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
    fun create(base: SwaggerBaseConfig, default: SwaggerConfig.Info, groups: Map<String, SwaggerConfig.Info>): List<Docket> {
        return if (groups.isEmpty()) {
            listOf(create(base, default))
        } else {
            groups.map { entry -> create(base, default, entry.value, entry.key) }
        }
    }

    fun create(base: SwaggerBaseConfig, default: SwaggerConfig.Info, group: SwaggerConfig.Info = default, name: String = "default") = //
            Docket(DocumentationType.SWAGGER_2) //
                    .groupName(group.name ?: name)
                    .consumes(group.consumes.ifEmpty { default.consumes })
                    .produces(group.produces.ifEmpty { default.produces })
                    .apiInfo(ApiInfo(group.title ?: default.title ?: base.get("title"),
                            group.description ?: default.description ?: base.get("description"),
                            group.version ?: default.version ?: base.get("version"),
                            group.termsOfServiceUrl ?: default.termsOfServiceUrl ?: base.get("termsOfServiceUrl"),
                            Contact(group.contactName ?: default.contactName ?: base.get("contactName"),
                                    group.contactUrl ?: default.contactUrl ?: base.get("contactUrl"),
                                    group.contactEmail ?: default.contactEmail ?: base.get("contactEmail")),
                            group.license ?: default.license ?: base.get("license"),
                            group.licenseUrl ?: default.licenseUrl ?: base.get("licenseUrl"),
                            group.vendorExtensions.ifEmpty { default.vendorExtensions }))
                    .select() //
                    .apis(RequestHandlerSelectors.basePackage(group.basePackage ?: default.basePackage ?: base.get("basePackage"))) //
                    .paths(PathSelectors.regex(group.regex ?: default.regex ?: base.get("regex"))) //
                    .paths(PathSelectors.ant(group.ant ?: default.ant ?: base.get("ant")))
                    .build()!!
}