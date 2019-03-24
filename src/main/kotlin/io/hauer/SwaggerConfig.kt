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
    }
}