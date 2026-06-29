package com.example.saneddriverapp.presentation.vehicle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class VehicleFormUiState {

    var vehicleType by mutableStateOf("")

    var vehicleModel by mutableStateOf("")

    var vehicleName by mutableStateOf("")

    var vehicleSequenceNumber by mutableStateOf("")

    var vehicleNumberPlateEn by mutableStateOf("")

    var vehicleNumberPlateAr by mutableStateOf("")

    var vehicleRegistrationExpiryDate by mutableStateOf("")

    var driverLicenseExpiryDate by mutableStateOf("")

}