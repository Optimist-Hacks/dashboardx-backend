package dev.optimist.dashboardx.service

import dev.optimist.dashboardx.dto.RawHousingData
import dev.optimist.dashboardx.model.DailyConsumption
import dev.optimist.dashboardx.model.Housing
import dev.optimist.dashboardx.repository.HousingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class HousingService(
        @Autowired val housingRepository: HousingRepository
) {

    fun processRawData(rawHousingData: RawHousingData) {
        val house1Elictricity = rawHousingData.data["E.1.1.2018 - 31.12.2018"]!!.filterKeys { it.toInt() < 313 }
        val house2Elictricity = rawHousingData.data["E.1.1.2019 - 31.12.2019"]!!.filterKeys { it.toInt() < 313 }
        val house1Heating = rawHousingData.data["H.1.1.2018 - 31.12.2018"]!!.filterKeys { it.toInt() < 313 }
        val house2Heating = rawHousingData.data["H.1.1.2019 - 31.12.2019"]!!.filterKeys { it.toInt() < 313 }
        val house1Water = rawHousingData.data["W.1.1.2018 - 31.12.2018"]!!.filterKeys { it.toInt() < 313 }
        val house2Water = rawHousingData.data["W.1.1.2019 - 31.12.2019"]!!.filterKeys { it.toInt() < 313 }

        for (i in 1..312) {
            submitDailyValues("4a944fb8-7e9b-4041-aed6-cded0c797562",
                    DailyConsumption(
                            date = LocalDate.ofYearDay(2019, i),
                            electricity = house1Elictricity[i.toString()]!!,
                            heating = house1Heating[i.toString()]!!,
                            water = house1Water[i.toString()]!!
                    )
                    )

            submitDailyValues("a4b0d1fa-7f52-465d-a34a-a5b8bf0e7a3e",
                    DailyConsumption(
                            date = LocalDate.ofYearDay(2019, i),
                            electricity = house2Elictricity[i.toString()]!!,
                            heating = house2Heating[i.toString()]!!,
                            water = house2Water[i.toString()]!!
                    )
            )
        }

    }

    fun submitDailyValues(housingId: String, dailyConsumption: DailyConsumption) {
        submitMultipleDailyValues(housingId, listOf(dailyConsumption))
    }

    fun submitMultipleDailyValues(housingId: String, dailyConsumption: List<DailyConsumption>) {
        val oldHousing = housingRepository
                .findById(housingId)
                .get()

        val newConsumptions = oldHousing.dailyConsumptions
                .toMutableList()

        newConsumptions.addAll(dailyConsumption)

        housingRepository.save(
                Housing(
                        id = oldHousing.id,
                        name = oldHousing.name,
                        dailyConsumptions = newConsumptions,
                        dailyEmotions = oldHousing.dailyEmotions
                )
        )
    }
}