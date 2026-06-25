package com.example.saneddriverapp.domain.model


data class PersonalInfo(
    val idNumber: String,
    val idExpiryDate: String,
    val fullName: String,
    val gender: String,
    val dob: String,
    val nationality: String,
    val city: String,
    val password: String,
    val imei: String,
    val mobile: String,
    val country: String
)