package dev.optimist.dashboardx.service

import dev.optimist.dashboardx.dto.WarningDto
import dev.optimist.dashboardx.model.Warning
import dev.optimist.dashboardx.repository.WarningRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WarningService(
        @Autowired val warningRepository: WarningRepository
) {
    fun save(warningDto: WarningDto) {
        warningRepository.save(Warning(
                name = warningDto.name,
                description = warningDto.description,
                housingId = warningDto.housingId
        ))
    }

    fun drop() {
        warningRepository.deleteAll()
    }
}
