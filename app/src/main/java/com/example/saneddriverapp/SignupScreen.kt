package com.example.saneddriverapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import android.util.Patterns
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun SignupScreen(
    navController: NavHostController,
    selectedCountry: String
) {

    val dialCode = when (selectedCountry) {
        "Saudi Arabia" -> "+966"
        "Kuwait" -> "+965"
        "Bahrain" -> "+973"
        else -> ""
    }
    var idOrIqama by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var acceptedTerms by remember { mutableStateOf(false) }

    val isPhoneValid = phoneNumber.length == 9
    val isIdOrIqamaValid = idOrIqama.length == 10


    val isFormValid =
        isPhoneValid &&
                isIdOrIqamaValid

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Enter your ID/Iqama and Phone Number",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "ID or Iqama",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = idOrIqama,
            onValueChange = {
                idOrIqama = it.filter { c -> c.isDigit() }.take(10)
            },
            label = { Text("Ex: 1234567890") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // PHONE
        Text(
            text = "Phone Number",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = dialCode, fontSize = 16.sp)



            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it.filter { c -> c.isDigit() }.take(9)
                },
                label = { Text("**********") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()

            )
        }

        Spacer(modifier = Modifier.height(12.dp))



        Spacer(modifier = Modifier.height(16.dp))

        // BUTTON
        Button(
            onClick = {
                navController.navigate(
                    "otp_verification/$phoneNumber/$idOrIqama"
                )            },
            enabled = isFormValid
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}