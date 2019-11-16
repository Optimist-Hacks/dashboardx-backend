package dev.optimist.dashboardx.api

import dev.optimist.dashboardx.api.entity.DetectFaceApiResponse
import dev.optimist.dashboardx.model.Image
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface FaceApiService {

    @POST("detect")
    fun detectFace(
            @Header("Content-Type") contentType: String = "application/json",
            @Header("Ocp-Apim-Subscription-Key") subscriptionKey: String,
            @Query("returnFaceId") faceId: Boolean = true,
            @Query("returnFaceLandmarks") fceLandmarks: Boolean = true,
            @Query("returnFaceAttributes") faceAttributes: String = "emotion",
            @Body imageUrl: Image
    ): Call<List<DetectFaceApiResponse>>

    @POST("detect")
    fun detectFace(
            @Header("Content-Type") contentType: String = "application/octet-stream",
            @Header("Ocp-Apim-Subscription-Key") subscriptionKey: String,
            @Query("returnFaceId") faceId: Boolean = true,
            @Query("returnFaceLandmarks") fceLandmarks: Boolean = true,
            @Query("returnFaceAttributes") faceAttributes: String = "emotion",
            @Body file: RequestBody
    ): Call<List<DetectFaceApiResponse>>

}