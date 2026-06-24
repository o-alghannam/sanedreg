package com.example.saneddriverapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.saneddriverapp.network.ReviewState
import com.example.saneddriverapp.network.ReviewViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
@Composable
fun StatusScreen(
    navController: NavHostController,
    viewModel: ReviewViewModel
) {
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
                text = "Application Status",
                fontSize = 24.sp
            )

            Spacer(Modifier.weight(1f))
        }

        Spacer(Modifier.height(32.dp))

        when (state) {

            is ReviewState.Loading -> {
                Text("Loading...")
            }

            is ReviewState.Verifying -> {
                Text("Verifying OTP...")
            }

            is ReviewState.Success -> {

                Text("Application Status")

                Spacer(Modifier.height(12.dp))

                Text("Status: ${state.status}")

                Spacer(Modifier.height(12.dp))

                if (!state.reason.isNullOrBlank()) {
                    Text("Reason: ${state.reason}")

                    Spacer(Modifier.height(12.dp))
                }

                if (state.fields.isNotEmpty()) {

                    Text("Fields to update:")

                    Spacer(Modifier.height(8.dp))

                    state.fields.forEach { field ->
                        Text("• $field")
                    }

                } else {

                    Text("No fields need updating.")
                }
            }

            is ReviewState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = Color.Red
                )
            }

            else -> {
                Text("No data")
            }
        }
    }
}