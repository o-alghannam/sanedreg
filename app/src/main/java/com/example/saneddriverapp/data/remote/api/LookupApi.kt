package com.example.saneddriverapp.data.remote.api

import com.example.saneddriverapp.data.remote.dto.response.CityResponse
import com.example.saneddriverapp.data.remote.dto.response.CountriesResponse
import com.example.saneddriverapp.data.remote.dto.response.VehicleModelResponse
import com.example.saneddriverapp.data.remote.dto.response.VehicleTypeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LookupApi {

    @GET("api/v1/drivers-management-portal/lookups/active-countries")
    suspend fun getCountries(): CountriesResponse

    @GET("api/v1/drivers-management-portal/lookups/cities")
    suspend fun getCities(
        @Query("countryCode") countryCode: String
    ): CityResponse

    @GET("api/v1/drivers-management-portal/lookups/vehicles-model")
    suspend fun getVehicleModels(): VehicleModelResponse

    @GET("api/v1/drivers-management-portal/lookups/vehicle-types")
    suspend fun getVehicleTypes(): VehicleTypeResponse
}