package io.hauer

import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

interface SwaggerProducer {
    fun generate(default: SwaggerConfig.Default, groups: Map<String, SwaggerConfig.Group>): List<Docket>
}

class SwaggerProducerImpl : SwaggerProducer {

    override fun generate(default: SwaggerConfig.Default, groups: Map<String, SwaggerConfig.Group>): List<Docket> {
        return if (groups.isEmpty()) {
            listOf(createDocket("default", SwaggerConfig.Group(default), default))
        } else {
            groups.map { entry -> createDocket(entry.key, entry.value, default) }
        }
    }

    private fun createDocket(name: String, group: SwaggerConfig.Group, default: SwaggerConfig.Default) = //
            Docket(DocumentationType.SWAGGER_2) //
                    .groupName(name)
                    .select() //
                    .apis(RequestHandlerSelectors.basePackage(group.basePackage ?: default.basePackage)) //
                    .paths(PathSelectors.regex(group.regex ?: default.regex)) //
                    .build()
                    .apiInfo(ApiInfo("", "", "", "", Contact("", "", ""), "", "", Collections.emptyList()))!!

}