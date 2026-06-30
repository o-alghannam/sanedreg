package com.example.saneddriverapp.data.repository

import com.example.saneddriverapp.data.remote.api.ReviewApi
import com.example.saneddriverapp.data.remote.dto.request.SendReviewOtpRequest
import com.example.saneddriverapp.data.remote.dto.request.VerifyReviewOtpRequest
import com.example.saneddriverapp.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val api: ReviewApi
) : ReviewRepository {

    override suspend fun sendReviewOtp(
        request: SendReviewOtpRequest
    ) =
        api.sendReviewOtp(request)

    override suspend fun verifyReviewOtp(
        request: VerifyReviewOtpRequest
    ) =
        api.verifyReviewOtp(request)
}