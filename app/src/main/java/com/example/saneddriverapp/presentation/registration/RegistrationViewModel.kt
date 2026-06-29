package com.example.saneddriverapp.presentation.registration


import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.saneddriverapp.data.remote.dto.request.SubmitInfoRequest
import com.example.saneddriverapp.data.repository.RegistrationRepository
import com.example.saneddriverapp.data.repository.UploadRepository
import com.example.saneddriverapp.domain.model.PersonalInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.example.saneddriverapp.data.remote.dto.response.VehicleInfoDto
import com.example.saneddriverapp.data.remote.dto.request.SignUpRequest
import com.example.saneddriverapp.presentation.vehicle.VehicleFormUiState

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val uploadRepository: UploadRepository
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
        uploadRepository.uploadAttachment(
            context = context,
            requestId = requestId,
            uri = uri,
            attachmentType = attachmentType
        )
    }

    suspend fun submitInfo(
        request: SubmitInfoRequest
    ) =
        registrationRepository.submitInfo(request)

    suspend fun finalizeApplication(
        requestId: Long
    ) {
        registrationRepository.finalizeApplication(requestId)
    }

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
    suspend fun signUp(
        request: SignUpRequest
    ) =
        registrationRepository.signUp(request)
}