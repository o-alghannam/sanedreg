package com.example.saneddriverapp.network.model

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