package io.hauer

import io.hauer.SwaggerAutoConfiguration.Companion.CONFIG_PREFIX
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author Jan Hauer
 * @since 1.0
 */

@ConfigurationProperties(prefix = CONFIG_PREFIX)
class SwaggerConfigurationProperties {

    /**
    The default/global values for dockets
     */
    var default = SwaggerDefaultDocketInformation()

    /**
     * Map for possible multiple dockets
     */
    var docket: Map<String, SwaggerDocketInformation>? = null

    class SwaggerDefaultDocketInformation {

        /**
        Restrict the showed spec to specified regex
         */
        var regex: String = ".*"

        /**
        Restrict the showed spec to specified base package and all sub packages.
         */
        var basePackage = ""
    }

    class SwaggerDocketInformation() {

        constructor(default: SwaggerDefaultDocketInformation) : this() {
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
    }
}