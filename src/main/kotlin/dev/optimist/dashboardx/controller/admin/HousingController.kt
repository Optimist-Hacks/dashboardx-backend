package dev.optimist.dashboardx.controller.admin

import dev.optimist.dashboardx.dto.HousingDto
import dev.optimist.dashboardx.dto.RawHousingData
import dev.optimist.dashboardx.model.DailyConsumption
import dev.optimist.dashboardx.model.Housing
import dev.optimist.dashboardx.repository.HousingRepository
import dev.optimist.dashboardx.service.HousingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/housing")
class HousingController(
        @Autowired val housingRepository: HousingRepository,
        @Autowired val housingService: HousingService
) {

    @GetMapping
    fun getAll(): List<Housing> {
        return housingRepository.findAll()
    }

    @PostMapping
    fun save(@RequestBody housing: HousingDto): Housing {
        return housingRepository.save(Housing(name = housing.name))
    }

    @PostMapping("/submitRawData")
    fun submitRawData(@RequestBody rawHousingData: RawHousingData) {
        housingService.processRawData(rawHousingData)
    }

    @PostMapping("/{housingId}/submitDaily")
    fun submitDaily(@PathVariable housingId: String, @RequestBody dailyConsumption: DailyConsumption) {
        housingService.submitDailyValues(housingId, dailyConsumption)
    }

    @PostMapping("/{housingId}/submitMultipleDays")
    fun submitMultipleDays(@PathVariable housingId: String, @RequestBody dailyConsumption: List<DailyConsumption>) {
        housingService.submitMultipleDailyValues(housingId, dailyConsumption)
    }
}