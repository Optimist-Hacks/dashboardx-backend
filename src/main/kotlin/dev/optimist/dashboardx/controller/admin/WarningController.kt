package dev.optimist.dashboardx.controller.admin

import dev.optimist.dashboardx.dto.WarningDto
import dev.optimist.dashboardx.repository.WarningRepository
import dev.optimist.dashboardx.service.WarningService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/warning")
class WarningController(
        @Autowired val warningService: WarningService
) {

    @PostMapping
    fun warning(@RequestBody warning: WarningDto) {
        warningService.save(warning)
    }

    @DeleteMapping
    fun dropWarnings() {
        warningService.drop()
    }
}