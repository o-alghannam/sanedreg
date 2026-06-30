package com.example.saneddriverapp.presentation.registration

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.saneddriverapp.data.remote.dto.request.SignUpRequest
import com.example.saneddriverapp.data.remote.dto.request.SubmitInfoRequest
import com.example.saneddriverapp.data.remote.dto.response.VehicleInfoDto
import com.example.saneddriverapp.domain.model.PersonalInfo
import com.example.saneddriverapp.domain.usecase.registration.FinalizeApplicationUseCase
import com.example.saneddriverapp.domain.usecase.registration.SignUpUseCase
import com.example.saneddriverapp.domain.usecase.registration.SubmitInfoUseCase
import com.example.saneddriverapp.domain.usecase.registration.UploadAttachmentUseCase
import com.example.saneddriverapp.presentation.vehicle.VehicleFormUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val submitInfoUseCase: SubmitInfoUseCase,
    private val finalizeApplicationUseCase: FinalizeApplicationUseCase,
    private val uploadAttachmentUseCase: UploadAttachmentUseCase
) : ViewModel() {

    val vehicleForm = VehicleFormUiState()

    var vehicleInfo: VehicleInfoDto? = null
    var personalInfo: PersonalInfo? = null

    private val _uiState =
        MutableStateFlow(RegistrationUiState())

    val uiState =
        _uiState.asStateFlow()

    suspend fun uploadAttachment(
        context: Context,
        requestId: Long,
        uri: Uri,
        attachmentType: String
    ) {
        uploadAttachmentUseCase(
            context = context,
            requestId = requestId,
            uri = uri,
            attachmentType = attachmentType
        )
    }

    suspend fun submitInfo(
        request: SubmitInfoRequest
    ) =
        submitInfoUseCase(request)

    suspend fun finalizeApplication(
        requestId: Long
    ) {
        finalizeApplicationUseCase(requestId)
    }

    suspend fun signUp(
        request: SignUpRequest
    ) =
        signUpUseCase(request)

    fun updateFullName(value: String) {
        _uiState.value =
            _uiState.value.copy(fullName = value)
    }

    fun updateGender(value: String) {
        _uiState.value =
            _uiState.value.copy(gender = value)
    }

    fun updateExpiryDate(value: String) {
        _uiState.value =
            _uiState.value.copy(expiryDate = value)
    }

    fun updateDateBirth(value: String) {
        _uiState.value =
            _uiState.value.copy(dateBirth = value)
    }

    fun updateNationality(value: String) {
        _uiState.value =
            _uiState.value.copy(nationality = value)
    }

    fun updateCity(value: String) {
        _uiState.value =
            _uiState.value.copy(city = value)
    }

    fun updatePassword(value: String) {
        _uiState.value =
            _uiState.value.copy(password = value)
    }

    fun updateConfirmPassword(value: String) {
        _uiState.value =
            _uiState.value.copy(confirmPassword = value)
    }
}