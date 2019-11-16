package dev.optimist.dashboardx.controller

import dev.optimist.dashboardx.service.FaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/face")
class FaceController (
        @Autowired
        private val faceService: FaceService
) {

    @PostMapping
    fun cameraPic(
            @RequestBody
            file: MultipartFile,
            housingId: String
    ): String {
        faceService.processCameraPic(housingId, file)
        return "Success"
    }
}