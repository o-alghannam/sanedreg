package com.example.saneddriverapp.data.mapper

import com.example.saneddriverapp.domain.model.City
import com.example.saneddriverapp.data.remote.dto.response.CityDto

fun CityDto.toDomain() =
    City(
        id = cityId,
        name = cityName
    )
