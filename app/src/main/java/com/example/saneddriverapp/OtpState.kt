package com.example.saneddriverapp

data class OtpState(
    val isLoading: Boolean = false,
    val requestId: Long? = null,
    val error: String? = null
)

