package com.example.saneddriverapp.presentation.city

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saneddriverapp.data.repository.LookupRepository
import com.example.saneddriverapp.domain.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val repository: LookupRepository
) : ViewModel() {

    private val _cities =
        MutableStateFlow<List<City>>(emptyList())

    val cities = _cities.asStateFlow()

    fun loadCities(countryCode: String) {

        viewModelScope.launch {

            try {

                _cities.value =
                    repository.getCities(countryCode)

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