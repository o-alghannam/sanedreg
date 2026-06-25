package com.example.saneddriverapp.presentation.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@Composable
fun StatusOtpScreen(
    navController: NavHostController,
    viewModel: ReviewViewModel
) {
    var otp by remember { mutableStateOf("") }
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 24.sp,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "Review Status",
                fontSize = 24.sp
            )

            Spacer(Modifier.weight(1f))
        }

        Spacer(Modifier.height(32.dp))

        Text("Enter OTP sent to your phone")

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = {
                otp = it.filter { c -> c.isDigit() }.take(4)
            },
            label = { Text("OTP") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = otp.isNotEmpty() && otp.length < 4
        )

        if (otp.isNotEmpty() && otp.length < 4) {
            Text(
                text = "OTP must be 4 digits",
                color = Color.Red
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.verifyOtp(otp)
            },
            enabled = otp.length == 4,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify")
        }

        Spacer(Modifier.height(16.dp))


        LaunchedEffect(state) {
            if (state is ReviewState.Success) {
                navController.navigate("status")
            }
        }
    }
}