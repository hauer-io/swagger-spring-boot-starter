package io.hauer.spring

import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

class SwaggerDocketFactory {
    fun create (name: String, info:SwaggerConfigurationProperties.SwaggerDocketInformation,
                default: SwaggerConfigurationProperties.SwaggerDefaultDocketInformation) : Docket
            =  Docket(DocumentationType.SWAGGER_2) //
                    .groupName(name)
                    .select() //
                    .apis(RequestHandlerSelectors.basePackage(info.basePackage?:default.basePackage)) //
                    .paths(PathSelectors.regex(info.regex?:default.regex)) //
                    .build()
                    .apiInfo(ApiInfo("", "", "", "", Contact("", "", ""), "", "", Collections.emptyList() ))
}