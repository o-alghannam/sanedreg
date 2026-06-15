package com.example.saneddriverapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.saneddriverapp.ui.theme.SanedDriverAppTheme

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