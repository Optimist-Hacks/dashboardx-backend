package dev.optimist.dashboardx.repository

import dev.optimist.dashboardx.model.Housing
import org.springframework.data.mongodb.repository.MongoRepository

interface HousingRepository: MongoRepository<Housing, String> {

    fun findById()
}