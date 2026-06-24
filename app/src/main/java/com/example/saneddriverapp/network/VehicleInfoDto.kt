package com.example.saneddriverapp.network


data class VehicleInfoDto(
    val vehicleType: String,
    val vehicleModel: String,
    val vehicleName: String,
    val vehicleSequenceNumber: String,
    val vehicleNumberPlateEn: String,
    val vehicleNumberPlateAr: String,
    val vehicleRegistrationExpiryDate: String,
    val driverLicenseExpiryDate: String
)