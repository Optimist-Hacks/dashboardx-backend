package dev.optimist.dashboardx.service

import dev.optimist.dashboardx.api.FaceApi
import dev.optimist.dashboardx.api.entity.DetectFaceApiResponse
import dev.optimist.dashboardx.api.entity.Emotion
import dev.optimist.dashboardx.api.entity.average
import dev.optimist.dashboardx.model.DailyEmotion
import dev.optimist.dashboardx.model.Face
import dev.optimist.dashboardx.model.Housing
import dev.optimist.dashboardx.repository.FaceRepository
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
        @Autowired val faceRepository: FaceRepository
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

        val averageEmotion = faces.map {
            it.faceAttributes.emotion
        }.average()

        faceRepository.save(Face(emotion = averageEmotion))

        logger.info("Finish process camera pic")
    }

    fun saveFace(face: Face) {
        faceRepository.save(face)
    }

    fun getLastN(n: Int): List<Emotion> {
        val allEmotions = faceRepository.findAll()

        allEmotions.sortByDescending { it.timestamp }
        return allEmotions.take(n).map { it.emotion }
    }
}
