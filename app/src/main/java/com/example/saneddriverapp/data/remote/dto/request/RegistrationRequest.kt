package com.example.saneddriverapp.data.remote.dto.request

import com.example.saneddriverapp.data.remote.dto.response.PersonalInfoDto
import com.example.saneddriverapp.data.remote.dto.response.VehicleInfoDto

data class RegistrationRequest(
    val requestId: Long,
    val personalInfo: PersonalInfoDto,
    val vehicleInfo: VehicleInfoDto
)