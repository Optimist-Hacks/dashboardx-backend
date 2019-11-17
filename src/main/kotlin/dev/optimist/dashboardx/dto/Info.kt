package dev.optimist.dashboardx.dto

import dev.optimist.dashboardx.api.entity.Emotion

data class Info (
        val electricityDaily: Double = 0.0,
        val heatingDaily: Double = 0.0,
        val waterDaily: Double = 0.0,
        val co2EmissionDaily: Double = 0.0,
        val leaderboard: List<Participant> = emptyList(),
        val emotionDaily: Emotion = Emotion.fuckYura(),
        val warning: WarningDto? = null
)