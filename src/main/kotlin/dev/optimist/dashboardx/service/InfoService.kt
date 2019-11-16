package dev.optimist.dashboardx.service

import dev.optimist.dashboardx.api.EmissionCalculatorApi
import dev.optimist.dashboardx.api.entity.Emotion
import dev.optimist.dashboardx.dto.Info
import dev.optimist.dashboardx.dto.Participant
import dev.optimist.dashboardx.model.DailyEmotion
import dev.optimist.dashboardx.model.Housing
import dev.optimist.dashboardx.repository.HousingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class InfoService(
        @Autowired val housingRepository: HousingRepository,
        @Autowired val emissionCalculatorApi: EmissionCalculatorApi,
        @Autowired val faceService: FaceService
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
                    co2Emission = yesterdayHousingData.co2Emission,
                    leaderboard = getLeaderboard(),
                    emotion = housingData.dailyEmotions.firstOrNull { it.date == LocalDate.now() }?.emotion?:Emotion.fuckYura()
            )
        }
    }

    fun getLeaderboard(): List<Participant> {
        return housingRepository
                .findAll()
                .map { housing ->
                    val lastMonthTotalEmission = housing.dailyConsumptions.filter { dailyConsumption ->
                        dailyConsumption.date.isAfter(LocalDate.now().withDayOfMonth(1))
                    }.sumByDouble { it.co2Emission }

                    Participant(
                            housingId = housing.id,
                            housingName = housing.name,
                            co2Emission = calculateTotalEmissionForCurrentMonth(housing)
                    )
                }
    }

    fun calculateTotalEmissionForCurrentMonth(housing: Housing): Double {
        val endOfLastMonth = LocalDate.now().withDayOfMonth(1).minusDays(1)
        val currHousing = housing.dailyConsumptions.filter {
            it.date.isAfter(endOfLastMonth)
        }
        val heating = currHousing.sumByDouble { it.heating }
        val electricity = currHousing.sumByDouble { it.electricity }
        return emissionCalculatorApi.yearlyHeatingEmission(heating.toInt()) +
        emissionCalculatorApi.yearlyElectricityEmission(electricity.toInt())
    }
}