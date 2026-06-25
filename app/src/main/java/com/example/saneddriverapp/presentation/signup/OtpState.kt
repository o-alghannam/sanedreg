package com.example.saneddriverapp.presentation.signup

data class OtpState(
    val isLoading: Boolean = false,
    val requestId: Long? = null,
    val error: String? = null
)