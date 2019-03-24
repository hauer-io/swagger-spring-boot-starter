package io.hauer.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class SwaggerApp

fun main(args: Array<String>) {
    runApplication<SwaggerApp>(*args)
}