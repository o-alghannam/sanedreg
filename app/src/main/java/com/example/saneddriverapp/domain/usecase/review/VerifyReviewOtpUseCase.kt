package com.example.saneddriverapp.domain.usecase.review

import com.example.saneddriverapp.data.remote.dto.request.VerifyReviewOtpRequest
import com.example.saneddriverapp.domain.repository.ReviewRepository
import javax.inject.Inject

class VerifyReviewOtpUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(
        request: VerifyReviewOtpRequest
    ) = repository.verifyReviewOtp(request)
}