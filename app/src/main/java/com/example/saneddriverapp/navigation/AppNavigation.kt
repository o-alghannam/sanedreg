package com.example.saneddriverapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saneddriverapp.presentation.country.CountrySelectionScreen
import com.example.saneddriverapp.presentation.home.HomeScreen
import com.example.saneddriverapp.presentation.registration.DriverInfoScreen
import com.example.saneddriverapp.presentation.registration.RegistrationViewModel
import com.example.saneddriverapp.presentation.review.ReviewIdScreen
import com.example.saneddriverapp.presentation.review.ReviewViewModel
import com.example.saneddriverapp.presentation.review.StatusOtpScreen
import com.example.saneddriverapp.presentation.review.StatusScreen
import com.example.saneddriverapp.presentation.signup.OtpScreen
import com.example.saneddriverapp.presentation.signup.SignupScreen
import com.example.saneddriverapp.presentation.upload.ApplicationSubmittedScreen
import com.example.saneddriverapp.presentation.upload.UploadDocs
import com.example.saneddriverapp.presentation.vehicle.VehichleInfo

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val registrationViewModel: RegistrationViewModel =
        hiltViewModel()

    val reviewViewModel: ReviewViewModel =
        hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController)
        }

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

        composable("country_selection") {
            CountrySelectionScreen(navController)
        }

        composable("signup/{country}") { backStackEntry ->

            val country =
                backStackEntry.arguments
                    ?.getString("country")
                    ?: "Saudi Arabia"

            SignupScreen(
                navController = navController,
                selectedCountry = country,
                registrationViewModel = registrationViewModel
            )
        }

        composable("driver_info/{requestId}/{idOrIqama}/{phone}") { backStackEntry ->

            val requestId =
                backStackEntry.arguments
                    ?.getString("requestId")
                    ?.toLongOrNull()
                    ?: 0L

            val idOrIqama =
                backStackEntry.arguments
                    ?.getString("idOrIqama")
                    ?: ""

            val phone =
                backStackEntry.arguments
                    ?.getString("phone")
                    ?: ""

            DriverInfoScreen(
                navController = navController,
                requestId = requestId,
                idOrIqama = idOrIqama,
                phoneNumber = phone,
                registrationViewModel = registrationViewModel
            )
        }

        composable("vehicle_info/{requestId}") { backStackEntry ->

            val requestId =
                backStackEntry.arguments
                    ?.getString("requestId")
                    ?.toLongOrNull()
                    ?: 0L

            VehichleInfo(
                navController = navController,
                requestId = requestId,
                registrationViewModel = registrationViewModel
            )
        }

        composable("uploadDocs/{requestId}") { backStackEntry ->

            val requestId =
                backStackEntry.arguments
                    ?.getString("requestId")
                    ?.toLongOrNull()
                    ?: 0L

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

            val phone =
                backStackEntry.arguments
                    ?.getString("phone")
                    ?: ""

            val id =
                backStackEntry.arguments
                    ?.getString("id")
                    ?: ""

            val key =
                backStackEntry.arguments
                    ?.getString("key")
                    ?: ""

            OtpScreen(
                navController = navController,
                phoneNumber = phone,
                idOrIqama = id,
                key = key
            )
        }
    }
}