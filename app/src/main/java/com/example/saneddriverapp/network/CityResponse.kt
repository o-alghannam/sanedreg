package com.example.saneddriverapp.network

data class CityResponse(
    val statusCode: Int,
    val data: List<CityDto>
)