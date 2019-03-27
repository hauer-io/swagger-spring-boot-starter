package io.hauer

import org.springframework.boot.context.properties.ConfigurationProperties
import springfox.documentation.service.VendorExtension
import java.util.*

/**
 * @author Jan Hauer
 * @since 1.0
 */

@ConfigurationProperties(prefix = "swagger")
class SwaggerConfig {

    /**
    The default/global values for group
     */
    var default = Info()

    /**
     * Map for possible multiple groups
     */
    var groups: Map<String, Info> = Collections.emptyMap()

    class Info {
        /**
        Restrict the showed spec to specified regex
         */
        var regex: String? = null

        /**
        Restrict the showed spec to specified base package and all sub packages.
         */
        var basePackage: String? = null

        /**
        Title of the group
         */
        var title: String? = null

        /**
        Description of the group
         */
        var description: String? = null

        /**
        Version of the group
         */
        var version: String? = null

        /**
        url to terms of service
         */
        var termsOfServiceUrl: String? = null

        /**
        license
         */
        var license: String? = null


        /**
        license url
         */
        var licenseUrl: String? = null


        /**
        contact name
         */
        var contactName: String? = null

        /**
        contact url
         */
        var contactUrl: String? = null

        /**
        contact email
         */
        var contactEmail: String? = null

        /**
         vendor extentions
         */
        var vendorExtensions = mutableListOf<VendorExtension<Any>>()
        /**
          produced media type values
         */
        var produces = mutableSetOf<String>()

        /**
         consumed media type values
         */
        var consumes = mutableSetOf<String>()
    }
}