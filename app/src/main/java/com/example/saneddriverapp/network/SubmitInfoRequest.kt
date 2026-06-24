package com.example.saneddriverapp.network

data class SubmitInfoRequest(
    val requestId: Long,
    val personalInfo: PersonalInfoDto,
    val vehicleInfo: VehicleInfoDto
)