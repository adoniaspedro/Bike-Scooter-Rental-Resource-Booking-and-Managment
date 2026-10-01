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
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

import androidx.activity.viewModels
import android.util.Log

// Navigation Imports
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private val bookingViewModel: BookingViewModel by viewModels()

    private val bookingActivityLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            // Log the result to see whether BookingActivity
            // returned successfully or was cancelled.
            Log.d(
                "MainActivity",
                "BookingActivity returned result code: ${result.resultCode}"
            )

            if (result.resultCode == RESULT_OK) {

                Log.d(
                    "MainActivity",
                    "BookingActivity returned RESULT_OK. " +
                            "Reading completed booking data."
                )

                // Get the Intent returned by BookingActivity.
                val data = result.data

                if (data != null) {
                    // Retrieve the customer's name.
                    val name = data.getStringExtra("name") ?: ""

                    // Retrieve the customer's email.
                    val email = data.getStringExtra("email") ?: ""

                    // Retrieve the Resource using Parcelable.
                    val resource = androidx.core.content.IntentCompat
                        .getParcelableExtra(
                            data,
                            "resource",
                            Resource::class.java
                        )

                    // Retrieve the rental period.
                    val rentalDays = data.getIntExtra(
                        "rentalDays",
                        0
                    )

                    // Retrieve the final booking cost.
                    val totalCredits = data.getIntExtra(
                        "totalCredits",
                        0
                    )

                    Log.d(
                        "MainActivity",
                        "Received booking result. " +
                                "Customer: $name, " +
                                "Resource: ${resource?.name}, " +
                                "Rental days: $rentalDays, " +
                                "Total: $totalCredits credits."
                    )

                    // Make sure the Resource was successfully returned.
                    if (resource != null) {

                        // Save the booking into the shared BookingViewModel
                        val bookingSaved = bookingViewModel.confirmBooking(
                            customerName = name,
                            customerEmail = email,
                            resource = resource,
                            rentalDays = rentalDays,
                            totalCredits = totalCredits
                        )

                        if (bookingSaved) {
                            Log.d(
                                "MainActivity",
                                "Booking successfully saved to BookingViewModel."
                            )

                        } else {
                            Log.d(
                                "MainActivity",
                                "Booking could not be saved by BookingViewModel."
                            )
                        }

                    } else {
                        Log.e(
                            "MainActivity",
                            "Booking result did not contain a valid Resource."
                        )
                    }

                } else {
                    Log.e(
                        "MainActivity",
                        "BookingActivity returned RESULT_OK " +
                                "but no result Intent was provided."
                    )
                }

            } else  {
                Log.d(
                    "MainActivity",
                    "BookingActivity was cancelled."
                )
            }
        }

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
                                },
                                onHistoryClick = {
                                    navController.navigate("history")
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
                                },
                                onBookNow = {
                                    val resource = bookingViewModel.basketResource.value
                                    val days = bookingViewModel.basketDays.value

                                    if (resource != null) {

                                        // Create the Intent for BookingActivity.
                                        val intent = Intent(
                                            this@MainActivity,
                                            BookingActivity::class.java
                                        )

                                        // Resource is Parcelable.
                                        intent.putExtra("resource", resource)

                                        // Rental period is passed as a normal Intent extra.
                                        intent.putExtra("rentalDays", days)

                                        // Pass the user's current credit balance to BookingActivity.
                                        intent.putExtra(
                                            "currentCredits",
                                            bookingViewModel.credits.value
                                        )

                                        Log.d(
                                            "MainActivity",
                                            "Launching BookingActivity with " +
                                                    "${resource.name}, $days day(s)."
                                        )
                                        bookingActivityLauncher.launch(intent)
                                    }
                                },
                                onHistoryClick = {
                                    navController.navigate("history")
                                }
                            )
                        }
                        // Booking History destination.
                        // This uses the SAME BookingViewModel as Browse and Basket.
                        composable("history") {
                            BookingHistoryScreen(
                                viewModel = bookingViewModel,

                                onBack = {
                                    navController.popBackStack()
                                },

                                onBrowseClick = {
                                    navController.navigate("browse")
                                },

                                onBasketClick = {
                                    navController.navigate("basket")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
