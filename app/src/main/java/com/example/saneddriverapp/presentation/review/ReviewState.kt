package com.example.saneddriverapp.presentation.review

sealed class ReviewState {
    object Idle : ReviewState()
    object Loading : ReviewState()
    object OtpSent : ReviewState()
    object Verifying : ReviewState()
    data class Success(val status: String, val reason: String?, val fields: List<String>) : ReviewState()
    data class Error(val message: String) : ReviewState()
}