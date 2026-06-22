package com.example.saneddriverapp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import android.os.Bundle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saneddriverapp.network.RegistrationViewModel
import com.example.saneddriverapp.network.ReviewViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // ✅ Existing working VM (DO NOT REMOVE)
    val registrationViewModel: RegistrationViewModel = viewModel()

    // ✅ Review VM (FIXED)
    val reviewViewModel: ReviewViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        // ---------------- HOME ----------------
        composable("home") {
            HomeScreen(navController)
        }

        // ---------------- REVIEW FLOW ----------------
        composable("review_id") {
            ReviewIdScreen(
                navController = navController,
                viewModel = reviewViewModel
            )
        }

        composable("otp") {
            StatusOtpScreen(
                navController = navController,
                viewModel = reviewViewModel
            )
        }

        composable("status") {
            StatusScreen(
                navController = navController,
                viewModel = reviewViewModel
            )
        }

        // ---------------- EXISTING FLOWS (UNCHANGED) ----------------

        composable("country_selection") {
            CountrySelectionScreen(navController)
        }

        composable("signup/{country}") { backStackEntry ->
            val country = backStackEntry.arguments?.getString("country")
                ?: "Saudi Arabia"

            SignupScreen(
                navController = navController,
                selectedCountry = country
            )
        }

        composable("driver_info/{requestId}/{idOrIqama}/{phone}") { backStackEntry ->

            val requestId = backStackEntry.arguments
                ?.getString("requestId")
                ?.toLongOrNull() ?: 0L

            val idOrIqama = backStackEntry.arguments?.getString("idOrIqama") ?: ""
            val phone = backStackEntry.arguments?.getString("phone") ?: ""

            DriverInfoScreen(
                navController = navController,
                requestId = requestId,
                idOrIqama = idOrIqama,
                phoneNumber = phone,
                registrationViewModel = registrationViewModel
            )
        }

        composable("vehicle_info/{requestId}") { backStackEntry ->

            val requestId = backStackEntry.arguments
                ?.getString("requestId")
                ?.toLongOrNull() ?: 0L

            VehichleInfo(
                navController = navController,
                requestId = requestId,
                registrationViewModel = registrationViewModel
            )
        }

        composable("uploadDocs/{requestId}") { backStackEntry ->

            val requestId = backStackEntry.arguments
                ?.getString("requestId")
                ?.toLongOrNull() ?: 0L

            UploadDocs(
                navController = navController,
                requestId = requestId,
                registrationViewModel = registrationViewModel
            )
        }

        composable("applicationSubmitted") {
            ApplicationSubmittedScreen(navController)
        }

        composable("otp_verification/{phone}/{id}/{key}") { backStackEntry ->

            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val key = backStackEntry.arguments?.getString("key") ?: ""

            OtpScreen(
                navController = navController,
                phoneNumber = phone,
                idOrIqama = id,
                key = key
            )
        }
    }
}
    @Composable
    fun HomeScreen(navController: NavHostController) {

        var idOrIqama by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.saned_logo),
                contentDescription = "Saned Logo",
                modifier = Modifier.size(180.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome to Saned",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = idOrIqama,
                onValueChange = { idOrIqama = it },
                label = { Text("ID / Iqama") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation =
                    if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector =
                                if (passwordVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // TODO: Login API call
                }
            ) {
                Text("                          Sign In                          ")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Want to join us as a Driver?")

                TextButton(
                    onClick = {
                        navController.navigate("country_selection")
                    }
                ) {
                    Text("Register")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navController.navigate("review_id")                }
            ) {
                Text("Review Your Request")
            }
        }
    }





