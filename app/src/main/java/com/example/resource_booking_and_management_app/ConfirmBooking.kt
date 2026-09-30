package com.example.resource_booking_and_management_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.IntentCompat

import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
import android.util.Log
import androidx.compose.ui.Alignment
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.text.font.FontStyle

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextButton

//Font Imports
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

//Imports for the dotted horizontal line
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color

//Imports for moving in between files
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

class ConfirmBooking : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the customer's name that was entered
        // on the BookingActivity screen.
        val name = intent.getStringExtra("name") ?: ""

        // Retrieve the customer's email that was entered
        // on the BookingActivity screen.
        val email = intent.getStringExtra("email") ?: ""

        // Resource implements Parcelable, so Android can
        // transfer the entire Resource object through the Intent.
        val resource = IntentCompat.getParcelableExtra(
            intent,
            "resource",
            Resource::class.java
        )

        // Retrieve the number of rental days selected on the
        // Browse screen.
        val rentalDays = intent.getIntExtra(
            "rentalDays",
            1
        )

        // The balance before the current booking is confirmed.
        val currentCredits = intent.getIntExtra(
            "currentCredits",
            500
        )
        Log.d(
            "ConfirmBooking",
            "Current credits received: $currentCredits"
        )

        // Only display the confirmation screen if a valid
        // Resource was successfully received.
        resource?.let {

            setContent {
                ConfirmBookingScreen(
                    name = name,
                    email = email,
                    resource = it,
                    rentalDays = rentalDays,
                    currentCredits = currentCredits,
                    onBack = {
                        finish()
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ConfirmBookingScreen(
        name: String,
        email: String,
        resource: Resource,
        rentalDays: Int,
        currentCredits: Int,
        onBack: () -> Unit
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        TextButton(
                            onClick = onBack
                        ) {
                            Text("< Back")
                        }
                    },
                    title = {}
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                Text(
                    text = "Confirm Booking",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = 110.dp
                    )
                )
                Text(
                    text = "Resource",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 25.dp, start = 5.dp)
                )

                // Displaying the actual resource selected by the user.
                Text(
                    text = resource.name,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 5.dp)
                )

                Text(
                    text = resource.category,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 4.dp, start = 5.dp)
                )

                Text(
                    text = "Location",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, start = 5.dp)
                )

                // Display the location belonging to the selected resource.
                Text(
                    text = resource.location,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 5.dp)
                )

                Text(
                    text = "Customer",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, start = 5.dp)
                )

                // Display the name entered by the customer
                // on the BookingActivity screen.
                Text(
                    text = name,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 5.dp)
                )

                // Display the email entered by the customer
                // on the BookingActivity screen.
                Text(
                    text = email,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 4.dp, start = 5.dp)
                )

                Text(
                    text = "Rental Period",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, start = 5.dp)
                )

                // The rental period was originally selected on the Browse
                // screen and transferred through the Basket and BookingActivity.
                Text(
                    text = "$rentalDays day(s)",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 5.dp)
                )

                Text(
                    text = "Price",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 20.dp, start = 5.dp)
                )

                // Display the price of the actual selected resource.
                Text(
                    text = "${resource.pricePerDay} credits / day",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(top = 8.dp, start = 5.dp)
                )

                // Dotted horizontal line
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .height(2.dp)
                ) {
                    drawLine(
                        color = Color.Black,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                        strokeWidth = 2f,
                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                            floatArrayOf(10f, 10f)
                        )
                    )
                }

                // Calculate the total booking cost using the resource's
                // price per day multiplied by the selected rental period.
                val totalCredits = resource.pricePerDay * rentalDays
                Text(
                    text = "Total Credits: $totalCredits Credits",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 30.dp, start = 5.dp)
                )

                val remainingCredits = currentCredits - totalCredits
                Text(
                    text = "Remaining Credits After Booking: $remainingCredits Credits",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 20.dp, start = 5.dp)
                )

                Row(
                    modifier = Modifier.padding(top = 120.dp, start = 50.dp)
                ) {
                    Button(
                        onClick = {
                            onBack() //return to the previous screen
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp)
                            .padding(start = 5.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            // Calculate the final booking cost.
                            val totalCredits = resource.pricePerDay * rentalDays

                            Log.d(
                                "ConfirmBooking",
                                "Confirm & Save pressed. " +
                                        "Customer: $name, " +
                                        "Resource: ${resource.name}, " +
                                        "Rental days: $rentalDays, " +
                                        "Total: $totalCredits credits."
                            )

                            // Create an Intent containing the final booking information.
                            // This Intent will be returned to BookingActivity.
                            val resultIntent = Intent().apply {

                                // Return the customer's name.
                                putExtra(
                                    "name",
                                    name
                                )

                                // Return the customer's email.
                                putExtra(
                                    "email",
                                    email
                                )

                                // Return the Resource using Parcelable.
                                putExtra(
                                    "resource",
                                    resource
                                )

                                // Return the rental period.
                                putExtra(
                                    "rentalDays",
                                    rentalDays
                                )

                                // Return the calculated total cost.
                                putExtra(
                                    "totalCredits",
                                    totalCredits
                                )
                            }

                            // Tell Android that the confirmation was successful.
                            setResult(
                                RESULT_OK,
                                resultIntent
                            )

                            Log.d(
                                "ConfirmBooking",
                                "Booking confirmed successfully. " +
                                        "Returning booking data to BookingActivity."
                            )

                            // Close ConfirmBooking.
                            // This causes the Activity Result launcher inside
                            // BookingActivity to receive RESULT_OK.
                            finish()
                        },
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp)
                            .padding(start = 5.dp)
                    ) {
                        Text("Confirm & Save")
                    }
                }
            }
        }
    }
}