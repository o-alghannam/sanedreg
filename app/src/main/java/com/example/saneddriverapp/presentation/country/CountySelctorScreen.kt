package com.example.saneddriverapp.presentation.country

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CountrySelectionScreen(
    navController: NavHostController
) {
    val viewModel: CountryViewModel = hiltViewModel()
    val countries by viewModel.countries.collectAsStateWithLifecycle()

    var selectedCountry by remember {
        mutableStateOf("")
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
        }

        Spacer(Modifier.height(40.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Select Your Country",
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            countries.forEach { country ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedCountry = country.name
                        }
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedCountry == country.name,
                        onClick = {
                            selectedCountry = country.name
                        }
                    )

                    Text(
                        text = country.name,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (selectedCountry.isNotBlank()) {
                Button(
                    onClick = {
                        navController.navigate(
                            "signup/${Uri.encode(selectedCountry)}"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continue")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}