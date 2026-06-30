package com.example.saneddriverapp.domain.usecase.registration

import com.example.saneddriverapp.domain.repository.RegistrationRepository
import javax.inject.Inject

class FinalizeApplicationUseCase @Inject constructor(
    private val repository: RegistrationRepository
) {
    suspend operator fun invoke(requestId: Long) =
        repository.finalizeApplication(requestId)
}