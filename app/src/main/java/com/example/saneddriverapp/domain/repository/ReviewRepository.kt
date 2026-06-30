package com.example.saneddriverapp.domain.repository

import com.example.saneddriverapp.data.remote.dto.request.SendReviewOtpRequest
import com.example.saneddriverapp.data.remote.dto.request.VerifyReviewOtpRequest
import com.example.saneddriverapp.data.remote.dto.response.SendReviewOtpResponse
import com.example.saneddriverapp.data.remote.dto.response.VerifyReviewOtpResponse
import retrofit2.Response

interface ReviewRepository {

    suspend fun sendReviewOtp(
        request: SendReviewOtpRequest
    ): Response<SendReviewOtpResponse>

    suspend fun verifyReviewOtp(
        request: VerifyReviewOtpRequest
    ): Response<VerifyReviewOtpResponse>
}