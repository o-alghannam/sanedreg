package com.example.saneddriverapp.data.remote.dto.response

data class SignUpResponse(
    val statusCode: Int,
    val title: String?,
    val message: String?,
    val data: SignUpData?
)

data class SignUpData(
    val key: String,
    val message: String?
)