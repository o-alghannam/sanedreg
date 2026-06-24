package com.example.saneddriverapp

import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import android.app.DatePickerDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.saneddriverapp.network.CountryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material3.OutlinedButton
import com.example.saneddriverapp.network.DocumentsDto
import com.example.saneddriverapp.network.SubmitInfoRequest
import com.example.saneddriverapp.network.RegistrationViewModel
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
//------------Personal Picture -------------------
@Composable
fun UploadDocs(
    navController: NavHostController,
    requestId: Long,
    registrationViewModel: RegistrationViewModel
) {
    val context = LocalContext.current
    val countryViewModel: CountryViewModel = viewModel()

    val scope = rememberCoroutineScope()
    Spacer(modifier = Modifier.height(64.dp))
    var personalPictureUri by remember { mutableStateOf<Uri?>(null) }
    var iqamaUri by remember { mutableStateOf<Uri?>(null) }
    var licenseUri by remember { mutableStateOf<Uri?>(null) }
    var registrationUri by remember { mutableStateOf<Uri?>(null) }
    var additionalFile1Uri by remember { mutableStateOf<Uri?>(null) }
    var additionalFile2Uri by remember { mutableStateOf<Uri?>(null) }
    val personalPicturePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        personalPictureUri = uri
    }
    val iqamaPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { iqamaUri = it }

    val licensePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { licenseUri = it }

    val registrationPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { registrationUri = it }
    val additionalFile1Picker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { additionalFile1Uri = it }
    val additionalFile2Picker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { additionalFile2Uri = it }
    val isFormValid =
        personalPictureUri != null &&
                iqamaUri != null &&
                licenseUri != null &&
                registrationUri != null

    Log.d("TEST", "personalInfo = ${registrationViewModel.personalInfo}")
    Log.d("TEST", "vehicleInfo = ${registrationViewModel.vehicleInfo}")
    val personalInfo = registrationViewModel.personalInfo
    val vehicleInfo = registrationViewModel.vehicleInfo

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

        SignUpStepper(currentStep = 2)

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Upload Personal Picture",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))


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

//------------National Id /Iqama copy ------------
        Text(
            text = "National Id /Iqama copy",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(18.dp))

        OutlinedButton(
            onClick = {
                iqamaPicker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(iqamaUri?.lastPathSegment ?: "Choose file")
        }
        Spacer(Modifier.height(12.dp))

//------------Driving Licence copy ----------------
        Text(
            text = "Driving Licence copy",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                licensePicker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(licenseUri?.lastPathSegment ?: "Choose file")
        }
        Text(
            text = "Accepted formats: PNG, JPEG, JPG",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(12.dp))

//------------vehicle registration copy ---------
        Text(
            text = "vehicle registration copy",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(18.dp))


        OutlinedButton(
            onClick = {
                registrationPicker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(registrationUri?.lastPathSegment ?: "Choose file")
        }
        Text(
            text = "Accepted formats: PNG, JPEG, JPG",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(32.dp))

        Text(
            text = "Please attach authority letter in case of rent,lease,medical report,or any other attachments",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(64.dp))

//Please attach authority letter in case of rent,lease,medical report,or any other attachments
//------------aditional file 1 ---------
        Text(
            text = "additional file1",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))


        OutlinedButton(
            onClick = {
                additionalFile1Picker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(additionalFile1Uri?.lastPathSegment ?: "Choose file")
        }

        Text(
            text = "Accepted formats: PNG, JPEG, JPG",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(12.dp))

//------------aditional file 2 ---------
        Text(
            text = "aditional file 2",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                additionalFile2Picker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(additionalFile2Uri?.lastPathSegment ?: "Choose file")
        }
        Text(
            text = "Accepted formats: PNG, JPEG, JPG",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(64.dp))



        Text(
            text = "by submitting this form, you agree to the Terms & Conditions of Saned Company.",
            style = MaterialTheme.typography.bodySmall
        )

        Button(
            onClick = {

                scope.launch {

                    try {

                        // 1. Submit Info
                        val request = SubmitInfoRequest(
                            requestId = requestId,
                            personalInfo = registrationViewModel.personalInfo!!,
                            vehicleInfo = registrationViewModel.vehicleInfo!!
                        )

                        Log.d("TEST", "Submitting info")
                        registrationViewModel.submitInfo(request)
                        Log.d("TEST", "submitInfo SUCCESS")

                        // 2. Upload Attachments

                        personalPictureUri?.let {
                            registrationViewModel.uploadAttachment(
                                context,
                                requestId,
                                it,
                                "PersonalPicture"
                            )
                        }

                        iqamaUri?.let {
                            registrationViewModel.uploadAttachment(
                                context,
                                requestId,
                                it,
                                "Iqama"
                            )
                        }

                        licenseUri?.let {
                            registrationViewModel.uploadAttachment(
                                context,
                                requestId,
                                it,
                                "DrivingLicense"
                            )
                        }

                        registrationUri?.let {
                            registrationViewModel.uploadAttachment(
                                context,
                                requestId,
                                it,
                                "VehicleRegistration"
                            )
                        }

                        additionalFile1Uri?.let {
                            registrationViewModel.uploadAttachment(
                                context,
                                requestId,
                                it,
                                "Additional1"
                            )
                        }

                        additionalFile2Uri?.let {
                            registrationViewModel.uploadAttachment(
                                context,
                                requestId,
                                it,
                                "Additional2"
                            )
                        }

                        // 3. Finalize
                        Log.d("TEST", "Finalizing")
                        registrationViewModel.finalizeApplication(requestId)

                        Log.d("TEST", "Navigate")
                        navController.navigate("applicationSubmitted")

                    } catch (e: HttpException) {

                        Log.e("TEST", "HTTP ${e.code()}")
                        Log.e(
                            "TEST",
                            e.response()?.errorBody()?.string() ?: "No error body"
                        )

                    } catch (e: Exception) {

                        Log.e("TEST", "Submit failed", e)

                    }
                }
            },
           enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        Spacer(Modifier.height(40.dp))

    }
}