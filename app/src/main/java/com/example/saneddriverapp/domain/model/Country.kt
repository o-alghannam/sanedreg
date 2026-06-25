package com.example.saneddriverapp.domain.model


data class Country(
    val id: Long,
    val name: String,
    val arabicName: String,
    val code: String,
    val phoneLength: Int
)