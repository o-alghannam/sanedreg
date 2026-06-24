package com.example.saneddriverapp.network

data class VehicleModelResponse(
    val statusCode: Int,
    val title: String?,
    val message: String?,
    val data: List<VehicleModelDto>
)