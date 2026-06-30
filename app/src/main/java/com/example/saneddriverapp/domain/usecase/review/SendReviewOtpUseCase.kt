package com.example.saneddriverapp.domain.usecase.review

import com.example.saneddriverapp.data.remote.dto.request.SendReviewOtpRequest
import com.example.saneddriverapp.domain.repository.ReviewRepository
import javax.inject.Inject

class SendReviewOtpUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend operator fun invoke(
        request: SendReviewOtpRequest
    ) = repository.sendReviewOtp(request)
}