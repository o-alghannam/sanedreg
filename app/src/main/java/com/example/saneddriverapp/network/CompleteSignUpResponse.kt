package com.example.saneddriverapp.network

data class CompleteSignUpResponse(
    val statusCode: Int,
    val title: String?,
    val message: String?,
    val data: CompleteSignUpData?
)

data class CompleteSignUpData(
    val requestId: Long
)
