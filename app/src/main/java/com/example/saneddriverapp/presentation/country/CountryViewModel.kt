package com.example.saneddriverapp.presentation.country

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saneddriverapp.data.repository.LookupRepository
import com.example.saneddriverapp.domain.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: LookupRepository
) : ViewModel() {

    private val _countries =
        MutableStateFlow<List<Country>>(emptyList())

    val countries: StateFlow<List<Country>>
        get() = _countries

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch {
            try {
                _countries.value =
                    repository.getCountries()
            } catch (e: Exception) {
                Log.e(
                    "COUNTRIES",
                    "Error loading countries",
                    e
                )
            }
        }
    }
}