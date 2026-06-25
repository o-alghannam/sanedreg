package com.example.saneddriverapp.data.remote.dto.response

data class VehicleInfoDto(
    val vehicleType: String,
    val vehicleModel: String,
    val vehicleName: String,
    val vehicleSequenceNumber: String,
    val vehicleNumberPlateEn: String,
    val vehicleNumberPlateAr: String,
    val vehicleRegistrationExpiryDate: String,
    val driverLicenseExpiryDate: String,
)