package com.example.saneddriverapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saneddriverapp.network.ReviewState
import com.example.saneddriverapp.network.ReviewViewModel
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
@Composable
fun ReviewIdScreen(
    navController: NavHostController,
    viewModel: ReviewViewModel
) {
    val state = viewModel.state
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
                fontSize = 24.sp,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "Review Application",
                fontSize = 24.sp
            )

            Spacer(Modifier.weight(1f))
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Enter National ID / Iqama",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.idNumber,
            onValueChange = {
                viewModel.onIdChange(
                    it.filter { char -> char.isDigit() }.take(10)
                )
            },
            label = { Text("ID / Iqama") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = viewModel.idNumber.isNotEmpty() &&
                    viewModel.idNumber.length < 10
        )

        if (viewModel.idNumber.isNotEmpty() &&
            viewModel.idNumber.length < 10
        ) {
            Text(
                text = "ID/Iqama incorrect",
                color = Color.Red
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.sendOtp()
            },
            enabled = viewModel.idNumber.length == 10,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }

        Spacer(Modifier.height(16.dp))

        when (state) {
            is ReviewState.Loading -> {
                Text("Sending OTP...")
            }

            is ReviewState.Error -> {
                Text(
                    text = state.message,
                    color = Color.Red
                )
            }

            else -> {}
        }

        Spacer(Modifier.height(40.dp))
    }

    LaunchedEffect(state) {
        if (state is ReviewState.OtpSent) {
            navController.navigate("otp")
        }
    }
}