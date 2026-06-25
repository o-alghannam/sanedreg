package com.example.saneddriverapp.data.remote.api

import com.example.saneddriverapp.data.remote.dto.request.SendReviewOtpRequest
import com.example.saneddriverapp.data.remote.dto.response.SendReviewOtpResponse
import com.example.saneddriverapp.data.remote.dto.request.VerifyReviewOtpRequest
import com.example.saneddriverapp.data.remote.dto.response.VerifyReviewOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReviewApi {

    @POST("api/v3/drivers-management/account/send-review-otp")
    suspend fun sendReviewOtp(
        @Body request: SendReviewOtpRequest
    ): Response<SendReviewOtpResponse>

    @POST("api/v3/drivers-management/account/verify-review-otp")
    suspend fun verifyReviewOtp(
        @Body request: VerifyReviewOtpRequest
    ): Response<VerifyReviewOtpResponse>
}