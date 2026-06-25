package com.example.saneddriverapp.data.remote.dto.response

data class SendReviewOtpResponse(
    val statusCode: Int,
    val title: String,
    val message: String,
    val data: SendReviewOtpData?
)

data class SendReviewOtpData(
    val key: String,
    val nationalId: String,
    val message: String
)
