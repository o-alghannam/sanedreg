package com.example.saneddriverapp.network


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saneddriverapp.network.CountryDto
import com.example.saneddriverapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.emptyList
class CountryViewModel : ViewModel() {


    private val _countries =
        MutableStateFlow<List<CountryDto>>(emptyList())

    val countries = _countries.asStateFlow()

    private val _cities =
        MutableStateFlow<List<CityDto>>(emptyList())

    val cities = _cities.asStateFlow()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.api.getCountries()

                _countries.value = response.data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadCities(countryCode: String) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.api.getCities(countryCode)

                _cities.value = response.data

            } catch (e: Exception) {
                Log.e("CITY", "Error loading cities", e)
            }
        }
    }

    private val _vehicleModels =
        MutableStateFlow<List<VehicleModelDto>>(emptyList())

    val vehicleModels = _vehicleModels.asStateFlow()

    fun loadVehicleModels() {
        viewModelScope.launch {
            try {
                Log.d("VEHICLE_API", "Loading vehicle models")

                val response =
                    RetrofitInstance.api.getVehicleModels()

                Log.d(
                    "VEHICLE_API",
                    "Received ${response.data.size} models"
                )

                _vehicleModels.value = response.data

            } catch (e: Exception) {
                Log.e(
                    "VEHICLE_API",
                    "ERROR",
                    e
                )
            }
        }
    }
    private val _vehicleTypes =
        MutableStateFlow<List<VehicleTypeDto>>(emptyList())

    val vehicleTypes = _vehicleTypes.asStateFlow()

    fun loadVehicleTypes() {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitInstance.api.getVehicleTypes()

                _vehicleTypes.value = response.data

                Log.d(
                    "VEHICLE_TYPES",
                    "Received ${response.data.size} types"
                )

            } catch (e: Exception) {
                Log.e(
                    "VEHICLE_TYPES",
                    "ERROR",
                    e
                )
            }
        }
    }
    /*fun submitInfo(
        request: SubmitInfoRequest
    ) {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {

                val response =
                    RetrofitInstance.api.submitInfo(request)

                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }

            } catch (e: Exception) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }*/
    fun submitInfo(
        request: SubmitInfoRequest,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.submitInfo(request)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }}
