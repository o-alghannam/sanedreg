package com.example.saneddriverapp.data.remote.api

import com.example.saneddriverapp.data.remote.dto.request.CompleteSignUpRequest
import com.example.saneddriverapp.data.remote.dto.response.CompleteSignUpResponse
import com.example.saneddriverapp.data.remote.dto.request.SubmitInfoRequest
import com.example.saneddriverapp.data.remote.dto.response.SubmitInfoResponse
import com.example.saneddriverapp.data.remote.dto.request.SignUpRequest
import com.example.saneddriverapp.data.remote.dto.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RegistrationApi {

    @POST("api/v3/drivers-management/account/sign-up")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): SignUpResponse

    @POST("api/v3/drivers-management/account/complete-sign-up")
    suspend fun completeSignUp(
        @Body request: CompleteSignUpRequest
    ): CompleteSignUpResponse

    @POST("api/v3/drivers-management/account/submit-info")
    suspend fun submitInfo(
        @Body request: SubmitInfoRequest
    ): SubmitInfoResponse

    @PUT(
        "api/v3/drivers-management/account/submit-registration-request/{requestId}"
    )
    suspend fun finalizeApplication(
        @Path("requestId")
        requestId: Long
    )
}