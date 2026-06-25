package com.example.saneddriverapp.data.mapper


import com.example.saneddriverapp.domain.model.Country
import com.example.saneddriverapp.data.remote.dto.response.CountryDto

fun CountryDto.toDomain(): Country {

    return Country(
        id = countryId,
        name = countryName ?: "",
        arabicName = countryNameAr ?: "",
        code = countryCode,
        phoneLength = phoneNumberLength
    )
}