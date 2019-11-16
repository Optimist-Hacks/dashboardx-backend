package dev.optimist.dashboardx.service

import dev.optimist.dashboardx.dto.Info
import dev.optimist.dashboardx.dto.Participant
import dev.optimist.dashboardx.repository.HousingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class InfoService(
        @Autowired val housingRepository: HousingRepository
) {

    fun getInfo(housingId: String): Info {
        val housingData = housingRepository.findById(housingId).get()

        val yesterdayHousingData = housingData.dailyConsumptions.firstOrNull {
            it.date == LocalDate.now().minusDays(1)
        }

        return if (yesterdayHousingData == null) {
            Info()
        } else {
            Info(
                    electricity = yesterdayHousingData.electricity,
                    heating = yesterdayHousingData.heating,
                    water = yesterdayHousingData.water,
                    co2Emission = yesterdayHousingData.co2Emission
            )
        }
    }
}