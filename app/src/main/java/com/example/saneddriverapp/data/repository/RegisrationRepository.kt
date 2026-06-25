package com.example.saneddriverapp.data.repository

import com.example.saneddriverapp.data.remote.api.RegistrationApi
import com.example.saneddriverapp.data.remote.dto.request.CompleteSignUpRequest
import com.example.saneddriverapp.data.remote.dto.request.SubmitInfoRequest
import javax.inject.Inject
import com.example.saneddriverapp.data.remote.dto.request.SignUpRequest
class RegistrationRepository @Inject constructor(
    private val api: RegistrationApi
) {
    suspend fun signUp(
        request: SignUpRequest
    ) =
        api.signUp(request)


    suspend fun completeSignUp(
        request: CompleteSignUpRequest
    ) =
        api.completeSignUp(request)

    suspend fun submitInfo(
        request: SubmitInfoRequest
    ) =
        api.submitInfo(request)

    suspend fun finalizeApplication(
        requestId: Long
    ) {
        api.finalizeApplication(requestId)
    }
}