package com.example.saneddriverapp.data.remote.dto.response

data class SubmitInfoResponse(
    val statusCode: Int,
    val title: String?,
    val message: String?,
    val data: SubmitInfoData?
)

data class SubmitInfoData(
    val requestId: Long,
    val status: String,
    val idNumber: String,
    val mobileNumber: String
)