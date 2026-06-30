package com.example.saneddriverapp.domain.usecase.lookup

import com.example.saneddriverapp.domain.repository.LookupRepository
import javax.inject.Inject

class GetVehicleModelsUseCase @Inject constructor(
    private val repository: LookupRepository
) {
    suspend operator fun invoke() =
        repository.getVehicleModels()
}