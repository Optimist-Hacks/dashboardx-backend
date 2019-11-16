package dev.optimist.dashboardx.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/noise")
class NoiseController {

    @PostMapping
    fun noise(@RequestBody noise: Double) {

    }
}