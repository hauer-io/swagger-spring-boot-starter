package io.hauer.spring

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * @author Jan Hauer
 * @since 1.0
 */

@ConfigurationProperties(prefix = CONFIG_PREFIX)
class SwaggerConfigurationProperties {

    /**
    Restrict the showed spec to specified regex
     */
    var regex = ".*"

    /**
    Restrict the showed spec to specified base package and all sub packages.
     */
    var basePackage = ""
}