package com.example.saneddriverapp.data.repository

import com.example.saneddriverapp.data.mapper.toDomain
import com.example.saneddriverapp.data.remote.api.LookupApi
import com.example.saneddriverapp.domain.model.City
import com.example.saneddriverapp.domain.model.Country
import com.example.saneddriverapp.domain.model.VehicleModel
import com.example.saneddriverapp.domain.model.VehicleType
import com.example.saneddriverapp.domain.repository.LookupRepository
import javax.inject.Inject

class LookupRepositoryImpl @Inject constructor(
    private val api: LookupApi
) : LookupRepository {

    override suspend fun getVehicleModels(): List<VehicleModel> {
        return api.getVehicleModels()
            .data
            ?.map { it.toDomain() }
            ?: emptyList()
    }

    override suspend fun getVehicleTypes(): List<VehicleType> {
        return api.getVehicleTypes()
            .data
            ?.map { it.toDomain() }
            ?: emptyList()
    }

    override suspend fun getCountries(): List<Country> {
        return api.getCountries()
            .data
            ?.map { it.toDomain() }
            ?: emptyList()
    }

    override suspend fun getCities(countryCode: String): List<City> {
        return api.getCities(countryCode)
            .data
            ?.map { it.toDomain() }
            ?: emptyList()
    }
}