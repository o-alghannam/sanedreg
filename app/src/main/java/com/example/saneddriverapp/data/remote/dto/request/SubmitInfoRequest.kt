package com.example.saneddriverapp.data.remote.dto.request

import com.example.saneddriverapp.domain.model.PersonalInfo
import com.example.saneddriverapp.data.remote.dto.response.VehicleInfoDto

data class SubmitInfoRequest(
    val requestId: Long,
    val personalInfo: PersonalInfo,
    val vehicleInfo: VehicleInfoDto
)