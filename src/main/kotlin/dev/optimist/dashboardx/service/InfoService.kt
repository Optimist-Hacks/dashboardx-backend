package dev.optimist.dashboardx.service

import dev.optimist.dashboardx.api.EmissionCalculatorApi
import dev.optimist.dashboardx.api.entity.Emotion
import dev.optimist.dashboardx.api.entity.average
import dev.optimist.dashboardx.dto.Info
import dev.optimist.dashboardx.dto.Participant
import dev.optimist.dashboardx.dto.WarningDto
import dev.optimist.dashboardx.model.Housing
import dev.optimist.dashboardx.model.Warning
import dev.optimist.dashboardx.repository.HousingRepository
import dev.optimist.dashboardx.repository.WarningRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

@Service
class InfoService(
        @Autowired val housingRepository: HousingRepository,
        @Autowired val emissionCalculatorApi: EmissionCalculatorApi,
        @Autowired val faceService: FaceService,
        @Autowired val warningRepository: WarningRepository
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
                    electricityDaily = yesterdayHousingData.electricity,
                    heatingDaily = yesterdayHousingData.heating,
                    waterDaily = yesterdayHousingData.water,
                    co2EmissionDaily = calculateEmission(yesterdayHousingData.heating, yesterdayHousingData.electricity),
                    leaderboard = getLeaderboard(),
                    emotionDaily = relevantEmotions(),
                    warning = relevantWarning(housingId)
            )
        }
    }

    private fun relevantEmotions(): Emotion {
        return faceService.getLastN(10).average()
    }

    private fun relevantWarning(housingId: String): WarningDto? {
        val relevantTime = Instant.now().minus(1, ChronoUnit.HOURS)
        val relevantWarning = warningRepository.findAll().filter { it.housingId == housingId && it.timestamp.isAfter(relevantTime) }.maxBy { it.timestamp }

        return if(relevantWarning == null) {
            null
        } else {
            WarningDto(
                    housingId = relevantWarning.housingId,
                    name = relevantWarning.name,
                    description = relevantWarning.description
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
                            co2EmissionMonthly = calculateTotalEmissionForCurrentMonth(housing)
                    )
                }
    }

    fun calculateEmission(heating: Double, electricity: Double): Double {
        return emissionCalculatorApi.yearlyHeatingEmission(heating.toInt()) +
                emissionCalculatorApi.yearlyElectricityEmission(electricity.toInt())
    }

    fun calculateTotalEmissionForCurrentMonth(housing: Housing): Double {
        val endOfLastMonth = LocalDate.now().withDayOfMonth(1).minusDays(1)
        val currHousing = housing.dailyConsumptions.filter {
            it.date.isAfter(endOfLastMonth)
        }
        val heating = currHousing.sumByDouble { it.heating }
        val electricity = currHousing.sumByDouble { it.electricity }
        return calculateEmission(heating, electricity)
    }
}