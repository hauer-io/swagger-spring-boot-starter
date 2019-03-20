package io.hauer.spring

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class SwaggerAppTest {

    @Test
    fun contextLoads() {
    }

}

@SpringBootApplication
class SwaggerExampleApplication

fun main(args: Array<String>) {
    runApplication<SwaggerExampleApplication>(*args)
}