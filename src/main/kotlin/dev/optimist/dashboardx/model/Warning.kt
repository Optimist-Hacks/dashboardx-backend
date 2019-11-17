package dev.optimist.dashboardx.model

import java.time.Instant
import java.util.*

data class Warning (
        val id: String = UUID.randomUUID().toString(),
        val housingId: String,
        val name: String,
        val description: String,
        val timestamp: Instant = Instant.now()
)