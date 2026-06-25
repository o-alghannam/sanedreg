package com.example.saneddriverapp.presentation.signup
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OtpScreen(
    navController: NavHostController,
    phoneNumber: String,
    idOrIqama: String,
    key: String
) {
    val viewModel: OtpViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()


    var secondsRemaining by remember { mutableStateOf(120) }
    val canResend = secondsRemaining == 0

    LaunchedEffect(secondsRemaining) {
        if (secondsRemaining > 0) {
            delay(1000)
            secondsRemaining--
        }
    }

    val lastFourDigits =
        if (phoneNumber.length >= 4)
            phoneNumber.takeLast(4)
        else
            phoneNumber

    var otp by remember { mutableStateOf("") }

    val isOtpValid =
        otp.length == 4 &&
                key.isNotBlank() &&
                key != "null"
    LaunchedEffect(state.requestId) {
        state.requestId?.let { requestId ->

                navController.navigate(
                    "driver_info/$requestId/$idOrIqama/$phoneNumber"
                )             {
                popUpTo("otp_verification/{phone}/{id}/{key}") {
                    inclusive = true
                }
            }
        }
    }

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
            }

        Text(
            text = "Enter the OTP sent to mobile number ending with $lastFourDigits",
            fontSize = 26.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = {
                otp = it.filter { c -> c.isDigit() }.take(4)
            },
            label = { Text("Enter OTP") },
            textStyle = LocalTextStyle.current.copy(color = Color.Black),

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = String.format(
                "%02d:%02d",
                secondsRemaining / 60,
                secondsRemaining % 60
            ),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Didn't receive the OTP?")

            TextButton(
                onClick = {
                    secondsRemaining = 120
                },
                enabled = canResend && !state.isLoading
            ) {
                Text("Resend OTP")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
        }

        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                val otpNumber = otp.toIntOrNull()

                if (otpNumber == null) {
                    return@Button
                }

                viewModel.verifyOtp(
                    idNumber = idOrIqama,
                    mobileNumber = phoneNumber,
                    otp = otpNumber,
                    key = key
                )
            },
            enabled = isOtpValid && !state.isLoading
        ) {
            Text("Verify")
        }
        Spacer(modifier = Modifier.height(40.dp))


    }
   // registrationViewModel.requestId = requestId

    //navController.navigate(
       // "driver_info/$requestId/$idOrIqama")
}


