package com.example.saneddriverapp.domain.usecase.registration

import com.example.saneddriverapp.data.remote.dto.request.SubmitInfoRequest
import com.example.saneddriverapp.domain.repository.RegistrationRepository
import javax.inject.Inject

class SubmitInfoUseCase @Inject constructor(
    private val repository: RegistrationRepository
) {
    suspend operator fun invoke(request: SubmitInfoRequest) =
        repository.submitInfo(request)
}