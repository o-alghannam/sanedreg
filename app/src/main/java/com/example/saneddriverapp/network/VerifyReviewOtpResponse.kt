package com.example.saneddriverapp.network

data class VerifyReviewOtpResponse(
    val statusCode: Int,
    val title: String,
    val message: String,
    val data: List<ApplicationStatusDto>?
)