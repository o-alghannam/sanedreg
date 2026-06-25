package com.example.saneddriverapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.saneddriverapp.navigation.AppNavigation
import com.example.saneddriverapp.ui.theme.SanedDriverAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SanedDriverAppTheme {
                AppNavigation()
            }
        }
    }
}