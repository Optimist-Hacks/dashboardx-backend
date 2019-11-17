package dev.optimist.dashboardx.model

import dev.optimist.dashboardx.api.entity.Emotion
import java.time.Instant
import java.util.*

data class Face (
        val id: String = UUID.randomUUID().toString(),
        val emotion: Emotion,
        val timestamp: Instant = Instant.now()
)