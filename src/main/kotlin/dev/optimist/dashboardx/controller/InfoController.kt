package dev.optimist.dashboardx.controller

import dev.optimist.dashboardx.model.Info
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/info")
class InfoController {

    @GetMapping
    fun info(): Info {
        return Info()
    }
}