package com.example.saneddriverapp.data.mapper

import com.example.saneddriverapp.domain.model.PersonalInfo
import com.example.saneddriverapp.data.remote.dto.response.PersonalInfoDto



fun PersonalInfo.toDto(): PersonalInfoDto {
    return PersonalInfoDto(
        idNumber = idNumber,
        idExpiryDate = idExpiryDate,
        fullName = fullName.trim(),
        gender = gender,
        dob = dob,
        nationality = nationality,
        city = city,
        password = password.trim(),
        mobile = mobile,
        country = country
    )
}