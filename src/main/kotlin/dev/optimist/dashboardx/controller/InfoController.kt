package dev.optimist.dashboardx.controller

import dev.optimist.dashboardx.dto.Info
import dev.optimist.dashboardx.service.InfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/info")
class InfoController(
        @Autowired val infoService: InfoService
) {

    @GetMapping
    fun info(@RequestParam housingId: String): Info {
        return infoService.getInfo(housingId)
    }
}