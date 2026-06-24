package com.example.saneddriverapp.network

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ReviewViewModel : ViewModel() {

    var otpKey: String = ""
        private set

    var state by mutableStateOf<ReviewState>(ReviewState.Idle)
        private set

    var idNumber by mutableStateOf("")
        private set

    fun onIdChange(value: String) {
        idNumber = value
    }

    fun sendOtp() {
        viewModelScope.launch {
            try {
                state = ReviewState.Loading

                val response = RetrofitInstance.api.sendReviewOtp(
                    SendReviewOtpRequest(
                        nationalId = idNumber
                    )
                )

                if (response.isSuccessful) {
                    otpKey = response.body()?.data?.key ?: ""
                    state = ReviewState.OtpSent
                } else {
                    state = ReviewState.Error(
                        response.errorBody()?.string() ?: "Failed to send OTP"
                    )
                }

            } catch (e: Exception) {
                state = ReviewState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun verifyOtp(otp: String) {
        viewModelScope.launch {
            try {
                state = ReviewState.Verifying

                println("===== VERIFY OTP =====")
                println("otpKey = '$otpKey'")
                println("nationalId = '$idNumber'")
                println("otp = '$otp'")

                val request = VerifyReviewOtpRequest(
                    key = otpKey,
                    otp = otp.toInt(),
                    nationalId = idNumber
                )

                println("REQUEST = $request")

                val response = RetrofitInstance.api.verifyReviewOtp(request)

                println("HTTP CODE = ${response.code()}")

                if (response.isSuccessful) {

                    val body = response.body()
                    println("SUCCESS = $body")

                    val first = body?.data?.firstOrNull()

                    if (first != null) {
                        state = ReviewState.Success(
                            status = first.status,
                            reason = first.reason,
                            fields = first.fieldsThatNeedUpdate.map { it.toString() }
                        )
                    } else {
                        state = ReviewState.Error("No application found")
                    }

                } else {

                    val error = response.errorBody()?.string()
                    println("ERROR BODY = $error")

                    state = ReviewState.Error(
                        error ?: "Verification failed"
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
                state = ReviewState.Error(e.message ?: "Unknown error")
            }
        }
    }
}