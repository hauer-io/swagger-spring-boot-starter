package io.hauer

import io.hauer.SwaggerAutoConfiguration.Companion.CONFIG_PREFIX
import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*

/**
 * @author Jan Hauer
 * @since 1.0
 */

@ConfigurationProperties(prefix = CONFIG_PREFIX)
class SwaggerConfig {

    /**
    The default/global values for group
     */
    var default = Default()

    /**
     * Map for possible multiple groups
     */
    var groups: Map<String, Group> = Collections.emptyMap()

    class Default {

        /**
        Restrict the showed spec to specified regex
         */
        var regex: String = ".*"

        /**
        Restrict the showed spec to specified base package and all sub packages.
         */
        var basePackage = ""

        /**
         Title of the group
         */
        var title = ""

        /**
         Description of the group
         */
        var description = ""

        /**
         Version of the group
         */
        var version = ""

        /**
         url to terms of service
         */
        var termsOfServiceUrl = ""

        /**
         license
         */
        var license = ""

        /**
        license url
         */
        var licenseUrl = ""


        /**
         contact name
         */
        var contactName = ""

        /**
         contact url
         */
        var contactUrl = ""

        /**
         contact email
         */
        var contactEmail = ""
    }


    class Group() {

        constructor(default: Default) : this() {
            this.regex = default.regex
            this.basePackage = default.basePackage
        }

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
    }
}