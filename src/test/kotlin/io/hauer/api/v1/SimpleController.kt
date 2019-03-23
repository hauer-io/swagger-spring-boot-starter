package io.hauer.api.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Jan Hauer
 * @since 1.0
 */

@RestController
@RequestMapping("/api/v1")
class SimpleController {
    @GetMapping
    fun hello() = "Hello World"
}