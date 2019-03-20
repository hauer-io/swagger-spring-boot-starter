package io.hauer.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author Jan Hauer
 * @since 1.0
 */

@SpringBootApplication
class SwaggerExampleApplication

fun main(args: Array<String>) {
    runApplication<SwaggerExampleApplication>(*args)
}
