package com.example.saneddriverapp.data.remote.dto.request

data class CompleteSignUpRequest(
    val idNumber: String,
    val mobileNumber: String,
    val otp: Int,
    val key: String
)