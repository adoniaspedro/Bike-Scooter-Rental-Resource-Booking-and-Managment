package com.example.resource_booking_and_management_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.resource_booking_and_management_app.ui.theme.Resource_Booking_and_Management_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Resource_Booking_and_Management_AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BrowseScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
