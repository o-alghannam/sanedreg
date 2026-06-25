package com.example.saneddriverapp.data.remote.dto.response

data class PersonalInfoDto(
    val idNumber: String,
    val idExpiryDate: String,
    val fullName: String,
    val gender: String,
    val dob: String,
    val nationality: String,
    val city: String,
    val password: String,
    val imei: String? = "356938035643809",
    val mobile: String? = null,
    val country: String
)