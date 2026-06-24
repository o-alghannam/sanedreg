package com.example.saneddriverapp.network

data class RequestData(
    val requestId: Long,
    val idNumber: String,
    val mobileNumber: String,
    val status: String
)