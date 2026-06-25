package com.example.saneddriverapp.data.remote.dto.request

data class VerifyReviewOtpRequest(
    val key: String,
    val otp: Int,
    val nationalId: String
)