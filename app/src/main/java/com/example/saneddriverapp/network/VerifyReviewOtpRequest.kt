package com.example.saneddriverapp.network

data class VerifyReviewOtpRequest(
    val key: String,
    val otp: Int,
    val nationalId: String
)