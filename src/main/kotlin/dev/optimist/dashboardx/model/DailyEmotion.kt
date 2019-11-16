package dev.optimist.dashboardx.model

import dev.optimist.dashboardx.api.entity.Emotion
import java.time.LocalDate

data class DailyEmotion(
        val emotion: Emotion,
        val date: LocalDate
)
