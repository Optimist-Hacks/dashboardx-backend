package dev.optimist.dashboardx.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File

@RestController
@RequestMapping("/face")
class FaceController {

    @PostMapping
    fun cameraPic(
            @RequestBody
            file: MultipartFile
    ): String {
        File("/home/gera/IdeaProjects/dashboardx/src/main/resources/file.jpg").writeBytes(file.bytes)
        return "Hello"
    }
}