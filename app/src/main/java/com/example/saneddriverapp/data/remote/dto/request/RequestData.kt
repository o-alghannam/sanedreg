package com.example.saneddriverapp.data.remote.dto.request

data class RequestData(
    val requestId: Long,
    val idNumber: String,
    val mobileNumber: String,
    val status: String
)