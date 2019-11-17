package dev.optimist.dashboardx.repository

import dev.optimist.dashboardx.api.entity.Emotion
import dev.optimist.dashboardx.model.Face
import org.springframework.data.mongodb.repository.MongoRepository

interface FaceRepository: MongoRepository<Face, String> {
}