package com.example.saneddriverapp.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saneddriverapp.data.remote.dto.request.CompleteSignUpRequest
import com.example.saneddriverapp.domain.usecase.registration.CompleteSignUpUseCase
import com.example.saneddriverapp.presentation.signup.OtpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val completeSignUpUseCase: CompleteSignUpUseCase
) : ViewModel() {

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
                it.copy(isLoading = true, error = null)
            }

            try {
                val response =
                    completeSignUpUseCase(
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