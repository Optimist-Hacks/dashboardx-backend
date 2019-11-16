package dev.optimist.dashboardx.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EmissionCalculatorApiService {

    @GET("HeatingEstimate")
    fun heatingEmission(
            @Query("type") type: String = "family",
            @Query("heatingMode") heatingMode: String = "district",
            @Query("area") area: Int = 90,
            @Query("buildYear") buildYear: Int = 2010,
            @Query("residents") residents: Int = 1,
            @Query("floorCount") floorCount: Int = 1,
            @Query("estimateConsumption") estimateConsumption: Boolean = false,
            @Query("districtHeatConsumption") districtHeatConsumption: Int,
            @Query("greenDistrictHeatPercentage") greenDistrictHeatPercentage: Int = 0
    ): Call<Double>

    @GET("ElectricityEstimateFromConsumption")
    fun electricityEmission(
            @Query("electricityConsumption") electricityConsumption: Int,
            @Query("residents") residents: Int = 1): Call<Double>
}
