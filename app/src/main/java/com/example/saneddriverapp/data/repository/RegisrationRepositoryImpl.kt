package com.example.saneddriverapp.data.repository

import com.example.saneddriverapp.data.remote.api.RegistrationApi
import com.example.saneddriverapp.data.remote.dto.request.CompleteSignUpRequest
import com.example.saneddriverapp.data.remote.dto.request.SignUpRequest
import com.example.saneddriverapp.data.remote.dto.request.SubmitInfoRequest
import com.example.saneddriverapp.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: RegistrationApi
) : RegistrationRepository {

    override suspend fun signUp(
        request: SignUpRequest
    ) =
        api.signUp(request)

    override suspend fun completeSignUp(
        request: CompleteSignUpRequest
    ) =
        api.completeSignUp(request)

    override suspend fun submitInfo(
        request: SubmitInfoRequest
    ) =
        api.submitInfo(request)

    override suspend fun finalizeApplication(
        requestId: Long
    ) {
        api.finalizeApplication(requestId)
    }
}