package com.example.saneddriverapp.presentation.vehicle

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saneddriverapp.data.repository.LookupRepository
import com.example.saneddriverapp.domain.model.VehicleModel
import com.example.saneddriverapp.domain.model.VehicleType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val repository: LookupRepository
) : ViewModel() {

    private val _vehicleModels =
        MutableStateFlow<List<VehicleModel>>(emptyList())

    val vehicleModels =
        _vehicleModels.asStateFlow()

    private val _vehicleTypes =
        MutableStateFlow<List<VehicleType>>(emptyList())

    val vehicleTypes =
        _vehicleTypes.asStateFlow()

    fun loadVehicleModels() {

        viewModelScope.launch {

            try {

                _vehicleModels.value =
                    repository.getVehicleModels()

            } catch (e: Exception) {

                Log.e(
                    "VEHICLE_MODELS",
                    "Error",
                    e
                )
            }
        }
    }

    fun loadVehicleTypes() {

        viewModelScope.launch {

            try {

                _vehicleTypes.value =
                    repository.getVehicleTypes()

            } catch (e: Exception) {

                Log.e(
                    "VEHICLE_TYPES",
                    "Error",
                    e
                )
            }
        }
    }
}