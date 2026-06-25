package com.example.saneddriverapp.presentation.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saneddriverapp.data.remote.dto.request.SendReviewOtpRequest
import com.example.saneddriverapp.data.remote.dto.request.VerifyReviewOtpRequest
import com.example.saneddriverapp.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repository: ReviewRepository
) : ViewModel() {

    var otpKey: String = ""
        private set

    var state by mutableStateOf<ReviewState>(ReviewState.Idle)
        private set

    var idNumber by mutableStateOf("")
        private set

    fun onIdChange(value: String) {
        idNumber =
            value
                .filter { char -> char.isDigit() }
                .take(10)
    }

    fun sendOtp() {
        viewModelScope.launch {
            try {
                state = ReviewState.Loading

                val response =
                    repository.sendReviewOtp(
                        SendReviewOtpRequest(
                            nationalId = idNumber
                        )
                    )

                if (response.isSuccessful) {
                    otpKey =
                        response.body()
                            ?.data
                            ?.key
                            ?: ""

                    state = ReviewState.OtpSent
                } else {
                    state =
                        ReviewState.Error(
                            response.errorBody()
                                ?.string()
                                ?: "Failed to send OTP"
                        )
                }

            } catch (e: Exception) {
                state =
                    ReviewState.Error(
                        e.message ?: "Unknown error"
                    )
            }
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            try {
                state = ReviewState.Verifying

                val request =
                    VerifyReviewOtpRequest(
                        key = otpKey,
                        otp = otp.toInt(),
                        nationalId = idNumber
                    )

                val response =
                    repository.verifyReviewOtp(request)

                if (response.isSuccessful) {

                    val first =
                        response.body()
                            ?.data
                            ?.firstOrNull()

                    if (first != null) {
                        state =
                            ReviewState.Success(
                                status = first.status,
                                reason = first.reason,
                                fields =
                                    first.fieldsThatNeedUpdate
                                        .map { it.toString() }
                            )
                    } else {
                        state =
                            ReviewState.Error(
                                "No application found"
                            )
                    }

                } else {
                    state =
                        ReviewState.Error(
                            response.errorBody()
                                ?.string()
                                ?: "Verification failed"
                        )
                }

            } catch (e: Exception) {
                e.printStackTrace()

                state =
                    ReviewState.Error(
                        e.message ?: "Unknown error"
                    )
            }
        }
    }
}