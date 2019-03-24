package io.hauer

import io.hauer.demo.SwaggerApp
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [SwaggerApp::class])
class LoadContext {

    @Test
    fun contextLoads() {
    }

}
