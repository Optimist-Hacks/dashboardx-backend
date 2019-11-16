package dev.optimist.dashboardx.api


import dev.optimist.dashboardx.api.entity.DetectFaceApiResponse
import dev.optimist.dashboardx.model.Image
import okhttp3.MediaType
import okhttp3.RequestBody
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component
@PropertySource("classpath:token.properties")
class FaceApi (
        @Autowired
        val env: Environment
){

    private val logger = LoggerFactory.getLogger(FaceApi::class.java)

    private var token: String = env.getProperty("secret-key")?:""
    private var baseUrl: String = env.getProperty("endpoint")?:""

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val api: FaceApiService

    init {
        api = retrofit.create(FaceApiService::class.java)
    }


    fun detectFace(image: Image): List<DetectFaceApiResponse> {
        val response = api.detectFace(
                subscriptionKey = token,
                imageUrl = image
        ).execute()

        return response.body()!!
    }

    fun detectFace(file: MultipartFile): List<DetectFaceApiResponse> {

        val requestFile = RequestBody.create(
                MediaType.parse("application/octet-stream"),
                file.bytes)
        val response = api.detectFace(
                subscriptionKey = token,
                file = requestFile
        ).execute()

        return response.body()!!
    }

}