package com.example.saneddriverapp.presentation.home
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.saneddriverapp.R


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





