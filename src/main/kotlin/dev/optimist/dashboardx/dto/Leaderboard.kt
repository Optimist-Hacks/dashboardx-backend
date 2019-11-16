package dev.optimist.dashboardx.dto

data class Leaderboard (
     val contesters: List<Participant> = emptyList()
)