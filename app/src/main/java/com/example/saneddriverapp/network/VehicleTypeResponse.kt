package com.example.saneddriverapp.network

data class VehicleTypeResponse(
    val statusCode: Int,
    val title: String?,
    val message: String?,
    val data: List<VehicleTypeDto>
)