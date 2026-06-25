package com.example.saneddriverapp.data.remote.dto.response

data class VehicleTypeResponse(
    val statusCode: Int,
    val title: String?,
    val message: String?,
    val data: List<VehicleTypeDto>
)