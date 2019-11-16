package dev.optimist.dashboardx.api

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component
class EmissionCalculatorApi {

    private val logger = LoggerFactory.getLogger(EmissionCalculatorApi::class.java)

    private var baseUrl = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/HousingCalculator/"

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val api: EmissionCalculatorApiService

    init {
        api = retrofit.create(EmissionCalculatorApiService::class.java)
    }

    fun yearlyHeatingEmission(heatingConsumption: Int): Double {
        logger.info("Making a call to heating emission API:\n consumption = $heatingConsumption")
        val response = api.heatingEmission(
                districtHeatConsumption = heatingConsumption
        ).execute()
        if(response.body() == null) {
            logger.info(response.message())
        }
        return response.body()?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, response.message())
    }

    fun yearlyElectricityEmission(elictricityConsumption: Int): Double {
        logger.info("Making a call to heating emission API:\n consumption = $elictricityConsumption")
        val response = api.electricityEmission(
                electricityConsumption = elictricityConsumption
        ).execute()
        if(response.body() == null) {
            logger.info(response.message())
        }
        return response.body()?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, response.message())
    }

}