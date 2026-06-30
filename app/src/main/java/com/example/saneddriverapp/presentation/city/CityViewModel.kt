package com.example.saneddriverapp.presentation.city

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saneddriverapp.domain.model.City
import com.example.saneddriverapp.domain.usecase.lookup.GetCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase
) : ViewModel() {

    private val _cities =
        MutableStateFlow<List<City>>(emptyList())

    val cities = _cities.asStateFlow()

    fun loadCities(countryCode: String) {
        viewModelScope.launch {
            try {
                _cities.value =
                    getCitiesUseCase(countryCode)
            } catch (e: Exception) {
                Log.e(
                    "CITY",
                    "Error loading cities",
                    e
                )
            }
        }
    }
}