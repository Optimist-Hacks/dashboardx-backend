package dev.optimist.dashboardx.api

import org.junit.Test

class EmissionCalculatorApiTest {

    private val emissionCalculatorApi = EmissionCalculatorApi()

    @Test
    fun yearlyHeatingEmission() {
        println(emissionCalculatorApi.yearlyHeatingEmission(5000))
    }

    @Test
    fun yearlyElictricityEmission() {
        println(emissionCalculatorApi.yearlyElectricityEmission(1000))
    }
}