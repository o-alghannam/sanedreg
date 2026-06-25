package com.example.saneddriverapp.data.repository

import com.example.saneddriverapp.data.remote.api.ReviewApi
import com.example.saneddriverapp.data.remote.dto.request.SendReviewOtpRequest
import com.example.saneddriverapp.data.remote.dto.request.VerifyReviewOtpRequest
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val api: ReviewApi
) {

    suspend fun sendReviewOtp(
        request: SendReviewOtpRequest
    ) =
        api.sendReviewOtp(request)

    suspend fun verifyReviewOtp(
        request: VerifyReviewOtpRequest
    ) =
        api.verifyReviewOtp(request)
}