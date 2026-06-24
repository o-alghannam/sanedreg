package com.example.saneddriverapp.network


data class CountriesResponse(
    val statusCode: Int,
    val title: String?,
    val message: String?,
    val data: List<CountryDto>
)