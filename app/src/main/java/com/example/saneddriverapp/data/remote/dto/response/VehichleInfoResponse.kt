package com.example.saneddriverapp.data.remote.dto.response

data class VehicleModelResponse(
    val statusCode: Int,
    val title: String?,
    val message: String?,
    val data: List<VehicleModelDto>
)