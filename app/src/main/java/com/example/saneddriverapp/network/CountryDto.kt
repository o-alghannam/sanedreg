package com.example.saneddriverapp.network

data class CountryDto(
    val countryId: Long,
    val countryName: String?,
    val countryNameAr: String?,
    val nationalityNameAr: String?,
    val nationalityName: String?,
    val countryCode: String,
    val currencyCode: String?,
    val idPattern: String?,
    val phoneNumberLength: Int,
    val operationNumber: String?
)