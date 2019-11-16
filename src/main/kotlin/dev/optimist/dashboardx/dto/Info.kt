package dev.optimist.dashboardx.dto

data class Info (
        val electricity: Double = 0.0,
        val heating: Double = 0.0,
        val water: Double = 0.0,
        val co2: Double = 0.0,
        val leaderboard: Leaderboard = Leaderboard()
)