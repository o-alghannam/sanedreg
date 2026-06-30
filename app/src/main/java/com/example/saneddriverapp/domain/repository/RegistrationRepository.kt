package com.example.saneddriverapp.domain.repository

import com.example.saneddriverapp.data.remote.dto.request.CompleteSignUpRequest
import com.example.saneddriverapp.data.remote.dto.request.SignUpRequest
import com.example.saneddriverapp.data.remote.dto.request.SubmitInfoRequest
import com.example.saneddriverapp.data.remote.dto.response.CompleteSignUpResponse
import com.example.saneddriverapp.data.remote.dto.response.SignUpResponse
import com.example.saneddriverapp.data.remote.dto.response.SubmitInfoResponse

interface RegistrationRepository {

    suspend fun signUp(
        request: SignUpRequest
    ): SignUpResponse

    suspend fun completeSignUp(
        request: CompleteSignUpRequest
    ): CompleteSignUpResponse

    suspend fun submitInfo(
        request: SubmitInfoRequest
    ): SubmitInfoResponse

    suspend fun finalizeApplication(
        requestId: Long
    )
}