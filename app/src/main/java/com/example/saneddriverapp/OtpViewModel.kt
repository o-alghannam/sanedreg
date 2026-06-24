package com.example.saneddriverapp
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saneddriverapp.network.CompleteSignUpRequest
import com.example.saneddriverapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update

class OtpViewModel : ViewModel() {

    private val _state = MutableStateFlow(OtpState())
    val state = _state.asStateFlow()

    fun verifyOtp(
        idNumber: String,
        mobileNumber: String,
        otp: Int,
        key: String
    ) {
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {

                val response =
                    RetrofitInstance.api.completeSignUp(
                        CompleteSignUpRequest(
                            idNumber = idNumber,
                            mobileNumber = mobileNumber,
                            otp = otp,
                            key = key
                        )
                    )

                _state.update {
                    it.copy(
                        isLoading = false,
                        requestId = response.data?.requestId
                    )
                }

            } catch (e: Exception) {

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }
}