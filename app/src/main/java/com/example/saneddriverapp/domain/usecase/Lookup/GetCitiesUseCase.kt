package com.example.saneddriverapp.domain.usecase.lookup

import com.example.saneddriverapp.domain.repository.LookupRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val repository: LookupRepository
) {
    suspend operator fun invoke(countryCode: String) =
        repository.getCities(countryCode)
}