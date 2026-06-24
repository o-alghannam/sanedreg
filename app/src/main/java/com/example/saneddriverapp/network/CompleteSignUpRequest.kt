package com.example.saneddriverapp.network

data class CompleteSignUpRequest(
    val idNumber: String,
    val mobileNumber: String,
    val otp: Int,
    val key: String
)