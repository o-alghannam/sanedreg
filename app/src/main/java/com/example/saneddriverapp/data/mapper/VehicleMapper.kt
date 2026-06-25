package com.example.saneddriverapp.data.mapper


import com.example.saneddriverapp.domain.model.VehicleModel
import com.example.saneddriverapp.domain.model.VehicleType
import com.example.saneddriverapp.data.remote.dto.response.VehicleModelDto
import com.example.saneddriverapp.data.remote.dto.response.VehicleTypeDto


fun VehicleModelDto.toDomain() =
    VehicleModel(
        id = id,
        name = modelName ?: ""
    )

fun VehicleTypeDto.toDomain() =
    VehicleType(
        id = id,
        name = name ?: ""
    )