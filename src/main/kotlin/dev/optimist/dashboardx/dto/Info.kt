package dev.optimist.dashboardx.dto

import dev.optimist.dashboardx.api.entity.Emotion

data class Info (
        val electricity: Double = 0.0,
        val heating: Double = 0.0,
        val water: Double = 0.0,
        val co2Emission: Double = 0.0,
        val leaderboard: List<Participant> = emptyList(),
        val emotion: Emotion = Emotion.fuckYura(),
        val warning: String? = null
)