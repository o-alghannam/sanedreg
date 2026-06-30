package com.example.saneddriverapp.presentation.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saneddriverapp.data.remote.dto.request.SendReviewOtpRequest
import com.example.saneddriverapp.data.remote.dto.request.VerifyReviewOtpRequest
import com.example.saneddriverapp.domain.usecase.review.SendReviewOtpUseCase
import com.example.saneddriverapp.domain.usecase.review.VerifyReviewOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val sendReviewOtpUseCase: SendReviewOtpUseCase,
    private val verifyReviewOtpUseCase: VerifyReviewOtpUseCase
) : ViewModel() {

    var otpKey: String = ""
        private set

    var state by mutableStateOf<ReviewState>(ReviewState.Idle)
        private set

    var idNumber by mutableStateOf("")
        private set

    fun onIdChange(value: String) {
        idNumber = value
            .filter { it.isDigit() }
            .take(10)
    }

    fun sendOtp() {
        viewModelScope.launch {
            try {
                state = ReviewState.Loading

                val response = sendReviewOtpUseCase(
                    SendReviewOtpRequest(
                        nationalId = idNumber
                    )
                )

                if (response.isSuccessful) {
                    otpKey = response.body()?.data?.key ?: ""
                    state = ReviewState.OtpSent
                } else {
                    state = ReviewState.Error(
                        response.errorBody()?.string()
                            ?: "Failed to send OTP"
                    )
                }

            } catch (e: Exception) {
                state = ReviewState.Error(
                    e.message ?: "Failed to send OTP"
                )
            }
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            try {
                state = ReviewState.Verifying

                val response = verifyReviewOtpUseCase(
                    VerifyReviewOtpRequest(
                        key = otpKey,
                        otp = otp.toInt(),
                        nationalId = idNumber
                    )
                )

                if (response.isSuccessful) {
                    val first = response.body()?.data?.firstOrNull()

                    state =
                        if (first != null) {
                            ReviewState.Success(
                                status = first.status,
                                reason = first.reason ?: "",
                                fields = first.fieldsThatNeedUpdate.map { field ->
                                    field.toString()
                                }
                            )
                        } else {
                            ReviewState.Error("No application found")
                        }
                } else {
                    state = ReviewState.Error(
                        response.errorBody()?.string()
                            ?: "Verification failed"
                    )
                }

            } catch (e: Exception) {
                state = ReviewState.Error(
                    e.message ?: "Verification failed"
                )
            }
        }
    }
}