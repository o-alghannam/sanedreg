package com.example.saneddriverapp.data.remote.dto.response

data class CompleteSignUpResponse(
    val statusCode: Int,
    val title: String?,
    val message: String?,
    val data: CompleteSignUpData?
)

data class CompleteSignUpData(
    val requestId: Long
)
