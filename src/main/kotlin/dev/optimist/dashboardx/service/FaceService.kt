package dev.optimist.dashboardx.service

import dev.optimist.dashboardx.api.FaceApi
import dev.optimist.dashboardx.api.entity.DetectFaceApiResponse
import dev.optimist.dashboardx.api.entity.Emotion
import dev.optimist.dashboardx.api.entity.average
import dev.optimist.dashboardx.model.DailyEmotion
import dev.optimist.dashboardx.model.Housing
import dev.optimist.dashboardx.repository.HousingRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDate
import java.util.*

@Component
class FaceService(
        @Autowired val faceApi: FaceApi,
        @Autowired val housingRepository: HousingRepository
) {

    private val logger = LoggerFactory.getLogger(FaceService::class.java)

    private fun saveImage(image: MultipartFile): String {
        val file = File("${UUID.randomUUID().toString()}.jpg")
        file.createNewFile()
        file.writeBytes(image.bytes)
        return file.absolutePath
    }


    fun detectFace(file: MultipartFile): List<DetectFaceApiResponse> {
        return faceApi.detectFace(file)
    }


    fun processCameraPic(housingId: String, file: MultipartFile) {

        logger.info("Starting to process camera pic")
        val faces = detectFace(file)
        logger.info("Successfully detected ${faces.size} faces")

        val oldHousingData = housingRepository.findById(housingId).get()

        var todayEmotion = oldHousingData.dailyEmotions.lastOrNull()
        val todayDate = LocalDate.now()

        val emotions = faces.map {
            it.faceAttributes.emotion
        }.toMutableList()
        when {
            todayEmotion == null -> todayEmotion = DailyEmotion(emotions.average(), todayDate, 1)
            todayDate > todayEmotion.date -> todayEmotion = DailyEmotion(emotions.average(), todayDate, 1)
            else -> todayEmotion = todayEmotion.calculateNewEmotion(emotions.average())
        }


        val newDailyEmotions = oldHousingData.dailyEmotions.toMutableList()
        newDailyEmotions.add(todayEmotion)
        val newHousingData = Housing(oldHousingData.id, oldHousingData.name, oldHousingData.dailyConsumptions, newDailyEmotions)

        housingRepository.save(newHousingData)

        logger.info("Finish process camera pic")
    }
}
