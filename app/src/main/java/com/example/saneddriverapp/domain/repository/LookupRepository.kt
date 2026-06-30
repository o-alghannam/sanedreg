package com.example.saneddriverapp.domain.repository

import com.example.saneddriverapp.domain.model.City
import com.example.saneddriverapp.domain.model.Country
import com.example.saneddriverapp.domain.model.VehicleModel
import com.example.saneddriverapp.domain.model.VehicleType

interface LookupRepository {

    suspend fun getVehicleModels(): List<VehicleModel>

    suspend fun getVehicleTypes(): List<VehicleType>

    suspend fun getCountries(): List<Country>

    suspend fun getCities(countryCode: String): List<City>
}