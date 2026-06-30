package com.example.saneddriverapp.domain.usecase.registration

import com.example.saneddriverapp.data.remote.dto.request.CompleteSignUpRequest
import com.example.saneddriverapp.domain.repository.RegistrationRepository
import javax.inject.Inject

class CompleteSignUpUseCase @Inject constructor(
    private val repository: RegistrationRepository
) {
    suspend operator fun invoke(
        request: CompleteSignUpRequest
    ) = repository.completeSignUp(request)
}