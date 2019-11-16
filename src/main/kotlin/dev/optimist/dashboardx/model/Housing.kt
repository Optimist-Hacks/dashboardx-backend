package dev.optimist.dashboardx.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Housing (
        val id: String = UUID.randomUUID().toString(),
        val name: String,
        val dailyConsumptions: List<DailyConsumption> = emptyList(),
        val dailyEmotions: List<DailyEmotion> = emptyList()

)