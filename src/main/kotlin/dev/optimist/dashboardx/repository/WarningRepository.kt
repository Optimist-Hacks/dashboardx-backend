package dev.optimist.dashboardx.repository

import dev.optimist.dashboardx.model.Warning
import org.springframework.data.mongodb.repository.MongoRepository

interface WarningRepository: MongoRepository<Warning, String> {

}
