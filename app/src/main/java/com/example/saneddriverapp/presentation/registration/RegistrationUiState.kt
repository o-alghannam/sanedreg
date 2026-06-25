package com.example.saneddriverapp.presentation.registration

data class RegistrationUiState(
    val fullName: String = "",
    val gender: String = "",
    val expiryDate: String = "",
    val dateBirth: String = "",
    val nationality: String = "",
    val city: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
