package com.example.saneddriverapp.presentation.upload

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saneddriverapp.data.remote.dto.request.SubmitInfoRequest
import com.example.saneddriverapp.presentation.registration.RegistrationViewModel
import com.example.saneddriverapp.presentation.registration.SignUpStepper
import kotlinx.coroutines.launch
import retrofit2.HttpException
@Composable
fun UploadDocs(
    navController: NavHostController,
    requestId: Long,
    registrationViewModel: RegistrationViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var personalPictureUri by remember { mutableStateOf<Uri?>(null) }
    var iqamaUri by remember { mutableStateOf<Uri?>(null) }
    var licenseUri by remember { mutableStateOf<Uri?>(null) }
    var registrationUri by remember { mutableStateOf<Uri?>(null) }
    var additionalFile1Uri by remember { mutableStateOf<Uri?>(null) }
    var additionalFile2Uri by remember { mutableStateOf<Uri?>(null) }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val personalPicturePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            personalPictureUri = uri
        }

    val iqamaPicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            iqamaUri = uri
        }

    val licensePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            licenseUri = uri
        }

    val registrationPicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            registrationUri = uri
        }

    val additionalFile1Picker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            additionalFile1Uri = uri
        }

    val additionalFile2Picker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            additionalFile2Uri = uri
        }

    val isFormValid =
        personalPictureUri != null &&
                iqamaUri != null &&
                licenseUri != null &&
                registrationUri != null

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 40.sp,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "Sign Up",
                fontSize = 24.sp
            )

            Spacer(Modifier.weight(1f))
        }

        Spacer(Modifier.height(24.dp))

        SignUpStepper(currentStep = 3)

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Upload Personal Picture",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                personalPicturePicker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                personalPictureUri?.lastPathSegment ?: "Choose file"
            )
        }

        Text(
            text = "Accepted formats: PNG, JPEG, JPG",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "National ID / Iqama Copy",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                iqamaPicker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                iqamaUri?.lastPathSegment ?: "Choose file"
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Driving Licence Copy",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                licensePicker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                licenseUri?.lastPathSegment ?: "Choose file"
            )
        }

        Text(
            text = "Accepted formats: PNG, JPEG, JPG",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Vehicle Registration Copy",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                registrationPicker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                registrationUri?.lastPathSegment ?: "Choose file"
            )
        }

        Text(
            text = "Accepted formats: PNG, JPEG, JPG",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Please attach authority letter in case of rent, lease, medical report, or any other attachments.",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Additional File 1",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                additionalFile1Picker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                additionalFile1Uri?.lastPathSegment ?: "Choose file"
            )
        }

        Text(
            text = "Accepted formats: PNG, JPEG, JPG",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Additional File 2",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                additionalFile2Picker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                additionalFile2Uri?.lastPathSegment ?: "Choose file"
            )
        }

        Text(
            text = "Accepted formats: PNG, JPEG, JPG",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(64.dp))

        Text(
            text = "By submitting this form, you agree to the Terms & Conditions of Saned Company.",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(16.dp))

        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(12.dp))
        }

        Button(
            onClick = {
                scope.launch {
                    try {
                        isLoading = true
                        errorMessage = null

                        val personalInfo =
                            registrationViewModel.personalInfo

                        val vehicleInfo =
                            registrationViewModel.vehicleInfo

                        if (personalInfo == null || vehicleInfo == null) {
                            errorMessage =
                                "Missing registration data. Please go back and complete the previous steps."

                            isLoading = false
                            return@launch
                        }

                        val request =
                            SubmitInfoRequest(
                                requestId = requestId,
                                personalInfo = personalInfo,
                                vehicleInfo = vehicleInfo
                            )

                        Log.d("UPLOAD_DOCS", "SubmitInfo request = $request")

                        val submitResponse =
                            registrationViewModel.submitInfo(request)

                        Log.d("UPLOAD_DOCS", "submitInfo SUCCESS = $submitResponse")
                        Log.d("UPLOAD_DOCS", "submitInfo success")

                        personalPictureUri?.let { uri ->
                            registrationViewModel.uploadAttachment(
                                context = context,
                                requestId = requestId,
                                uri = uri,
                                attachmentType = "PersonalPicture"
                            )
                        }

                        iqamaUri?.let { uri ->
                            registrationViewModel.uploadAttachment(
                                context = context,
                                requestId = requestId,
                                uri = uri,
                                attachmentType = "Iqama"
                            )
                        }

                        licenseUri?.let { uri ->
                            registrationViewModel.uploadAttachment(
                                context = context,
                                requestId = requestId,
                                uri = uri,
                                attachmentType = "DrivingLicense"
                            )
                        }

                        registrationUri?.let { uri ->
                            registrationViewModel.uploadAttachment(
                                context = context,
                                requestId = requestId,
                                uri = uri,
                                attachmentType = "VehicleRegistration"
                            )
                        }

                        additionalFile1Uri?.let { uri ->
                            registrationViewModel.uploadAttachment(
                                context = context,
                                requestId = requestId,
                                uri = uri,
                                attachmentType = "Additional1"
                            )
                        }

                        additionalFile2Uri?.let { uri ->
                            registrationViewModel.uploadAttachment(
                                context = context,
                                requestId = requestId,
                                uri = uri,
                                attachmentType = "Additional2"
                            )
                        }

                        Log.d("UPLOAD_DOCS", "Finalizing application")

                        registrationViewModel.finalizeApplication(requestId)

                        navController.navigate("applicationSubmitted")

                    } catch (e: HttpException) {
                        Log.e("UPLOAD_DOCS", "HTTP ${e.code()}")
                        Log.e(
                            "UPLOAD_DOCS",
                            e.response()?.errorBody()?.string()
                                ?: "No error body"
                        )

                        errorMessage =
                            "Server error: ${e.code()}"

                    } catch (e: Exception) {
                        Log.e("UPLOAD_DOCS", "Submit failed", e)

                        errorMessage =
                            e.message ?: "Submit failed"

                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = isFormValid && !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (isLoading) "Submitting..."
                else "Submit"
            )
        }

        Spacer(Modifier.height(40.dp))
    }
}