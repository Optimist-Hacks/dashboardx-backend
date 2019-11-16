package dev.optimist.dashboardx.controller.admin

import dev.optimist.dashboardx.dto.HousingDto
import dev.optimist.dashboardx.model.Housing
import dev.optimist.dashboardx.repository.HousingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/housing")
class HousingController(
        @Autowired val housingRepository: HousingRepository
) {

    @GetMapping
    fun getAll(): List<Housing> {
        return housingRepository.findAll()
    }

    @PostMapping
    fun save(@RequestBody housing: HousingDto): Housing {
        return housingRepository.save(Housing(name = housing.name))
    }

}