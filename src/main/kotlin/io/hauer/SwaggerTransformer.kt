package io.hauer

import springfox.documentation.spring.web.plugins.Docket

/**
 * @author Jan Hauer
 * @since 1.0
 */
interface SwaggerTransformer {
    fun transform (docket : Docket) = docket
}