package dev.optimist.dashboardx.model

import java.time.LocalDate

data class DailyConsumption (
        val date: LocalDate = LocalDate.now(),
        val electricity: Double = 0.0,
        val heating: Double = 0.0,
        val water: Double = 0.0,
        val co2Emission: Double = 0.0
)
