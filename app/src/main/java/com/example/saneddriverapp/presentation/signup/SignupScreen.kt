package com.example.saneddriverapp.presentation.signup

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saneddriverapp.data.remote.dto.request.SignUpRequest
import com.example.saneddriverapp.presentation.registration.RegistrationViewModel
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    navController: NavHostController,
    selectedCountry: String,
    registrationViewModel: RegistrationViewModel
) {
    val dialCode =
        when (selectedCountry) {
            "Saudi Arabia" -> "966"
            "Kuwait" -> "965"
            "Bahrain" -> "973"
            else -> ""
        }

    var idOrIqama by remember {
        mutableStateOf("")
    }

    var phoneNumber by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val isPhoneValid =
        phoneNumber.length == 9

    val isIdOrIqamaValid =
        idOrIqama.length == 10

    val fullPhoneNumber =
        "$dialCode$phoneNumber"

    val isFormValid =
        isPhoneValid &&
                isIdOrIqamaValid &&
                dialCode.isNotBlank()

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

        Spacer(Modifier.height(120.dp))

        Text(
            text = "Enter your ID/Iqama and Phone Number",
            fontSize = 18.sp
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = "ID or Iqama",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = idOrIqama,
            onValueChange = { value ->
                idOrIqama =
                    value
                        .filter { char -> char.isDigit() }
                        .take(10)
            },
            label = {
                Text("Ex: 1234567890")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (idOrIqama.isNotEmpty() && !isIdOrIqamaValid) {
            Text(
                text = "ID/Iqama must be 10 digits",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Phone Number",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dialCode,
                fontSize = 16.sp
            )

            Spacer(Modifier.width(8.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { value ->
                    phoneNumber =
                        value
                            .filter { char -> char.isDigit() }
                            .take(9)
                },
                label = {
                    Text("*********")
                },
                textStyle = LocalTextStyle.current.copy(
                    color = Color.Black
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (phoneNumber.isNotEmpty() && !isPhoneValid) {
            Text(
                text = "Phone number must be 9 digits",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(16.dp))

        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))
        }

        Button(
            onClick = {
                scope.launch {
                    try {
                        isLoading = true
                        errorMessage = null

                        val response =
                            registrationViewModel.signUp(
                                SignUpRequest(
                                    idNumber = idOrIqama,
                                    mobileNumber = fullPhoneNumber
                                )
                            )

                        val key =
                            response.data?.key

                        if (key.isNullOrBlank()) {
                            errorMessage =
                                "OTP key is missing from server response"
                            return@launch
                        }

                        navController.navigate(
                            "otp_verification/" +
                                    "${Uri.encode(fullPhoneNumber)}/" +
                                    "${Uri.encode(idOrIqama)}/" +
                                    Uri.encode(key)
                        )

                    } catch (e: Exception) {
                        e.printStackTrace()

                        errorMessage =
                            e.message ?: "Sign up failed"

                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = isFormValid && !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (isLoading) "Sending..."
                else "Sign Up"
            )
        }

        Spacer(Modifier.height(40.dp))
    }
}