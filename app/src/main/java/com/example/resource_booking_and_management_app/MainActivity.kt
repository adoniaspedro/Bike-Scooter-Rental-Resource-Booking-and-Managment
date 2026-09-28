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

import androidx.activity.viewModels
import android.util.Log

// Navigation Imports
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private val bookingViewModel: BookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(
            "MainActivity",
            "MainActivity created. BookingViewModel attached."
        )
        enableEdgeToEdge()
        setContent {
            Resource_Booking_and_Management_AppTheme {

                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "browse",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable("browse") {
                            BrowseScreen(
                                viewModel = bookingViewModel,
                                modifier = Modifier,
                                onBasketClick = {
                                    navController.navigate("basket")
                                }
                            )
                        }

                        composable("basket") {
                            BookingBasketScreen(
                                viewModel = bookingViewModel,
                                onBack = {
                                    navController.popBackStack()
                                },
                                onBrowseClick = {
                                    navController.navigate("browse") {
                                        popUpTo("browse") {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
