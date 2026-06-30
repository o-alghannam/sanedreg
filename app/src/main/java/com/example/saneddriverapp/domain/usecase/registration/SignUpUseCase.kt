package com.example.saneddriverapp.domain.usecase.registration

import com.example.saneddriverapp.data.remote.dto.request.SignUpRequest
import com.example.saneddriverapp.domain.repository.RegistrationRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: RegistrationRepository
) {
    suspend operator fun invoke(
        request: SignUpRequest
    ) = repository.signUp(request)
}