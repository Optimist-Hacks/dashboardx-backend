package dev.optimist.dashboardx.model

import dev.optimist.dashboardx.api.entity.Emotion
import dev.optimist.dashboardx.api.entity.average
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

data class DailyEmotion(
        val emotion: Emotion,
        val date: LocalDate,
        val count: Int

) {
    fun calculateNewEmotion(e: Emotion): DailyEmotion {
        val lst = listOf(this.emotion, e)
        val c = count + 1
        return DailyEmotion(Emotion(
                lst.sumByDouble { it.anger }/c,
                lst.sumByDouble { it.contempt }/c,
                lst.sumByDouble { it.disgust }/c,
                lst.sumByDouble { it.fear }/c,
                lst.sumByDouble { it.happiness }/c,
                lst.sumByDouble { it.neutral }/c,
                lst.sumByDouble { it.sadness }/c,
                lst.sumByDouble { it.surprise }/c
        ), date, c)
    }
}
