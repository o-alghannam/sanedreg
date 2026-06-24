package com.example.saneddriverapp
import com.example.saneddriverapp.network.PersonalInfoDto
import com.example.saneddriverapp.network.VehicleInfoDto
data class RegistrationRequest(
    val requestId: Long,
    val personalInfo: PersonalInfoDto,
    val vehicleInfo: VehicleInfoDto
)
