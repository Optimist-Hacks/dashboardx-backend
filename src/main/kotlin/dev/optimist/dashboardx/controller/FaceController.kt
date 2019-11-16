package dev.optimist.dashboardx.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/face")
class FaceController {

    @PostMapping("/cameraPic")
    fun cameraPic(
            @RequestBody
            file: MultipartFile
    ): String {
        return "Hello"
    }
}