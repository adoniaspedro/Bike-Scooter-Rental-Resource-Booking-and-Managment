package com.example.resource_booking_and_management_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Row
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
import androidx.core.content.IntentCompat
import android.util.Log

//Font Imports
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Image Imports for the Logo
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

//Imports for the dotted horizontal line
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color

//Imports for Textfield
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

//Imports for moving in between files
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

// Import for launching ConfirmBooking and receiving its result.
import androidx.activity.result.contract.ActivityResultContracts


class BookingActivity : ComponentActivity() {

    private val confirmBookingLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->

            // RESULT_OK means the user confirmed the booking.
            // RESULT_CANCELED means the user cancelled or backed out.
            Log.d(
                "BookingActivity",
                "ConfirmBooking returned result code: ${result.resultCode}"
            )

            if (result.resultCode == RESULT_OK) {

                // The booking has been confirmed.
                // The actual booking data is returned through the Intent.
                // We will forward this data to MainActivity next.
                val resultIntent = result.data

                Log.d(
                    "BookingActivity",
                    "ConfirmBooking returned RESULT_OK. " +
                            "Preparing booking result for MainActivity."
                )

                // Return the successful booking result to MainActivity.
                setResult(
                    RESULT_OK,
                    resultIntent
                )

                // Finish BookingActivity so the result travels back
                // to the MainActivity Activity Result launcher.
                finish()

            } else {

                // The user cancelled the confirmation screen.
                // This allows the user to remain on the BookingActivity
                // screen and make changes if needed.
                Log.d(
                    "BookingActivity",
                    "ConfirmBooking was cancelled. " +
                            "Staying on BookingActivity."
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val resource = IntentCompat.getParcelableExtra(
            intent,
            "resource",
            Resource::class.java
        )

        val rentalDays = intent.getIntExtra(
            "rentalDays",
            1
        )
        // Receive the user's current credit balance from MainActivity.
        // This represents the balance BEFORE the current booking is confirmed.
        val currentCredits = intent.getIntExtra(
            "currentCredits",
            500
        )
        Log.d(
            "BookingActivity",
            "Current credits received: $currentCredits"
        )

        // BookingActivity cannot work without a selected resource.
        // If the resource was not passed correctly through the Intent,
        // log the problem and close this Activity.
        if (resource == null) {

            Log.e(
                "BookingActivity",
                "No Resource was received from MainActivity. " +
                        "Closing BookingActivity."
            )

            finish()
            return
        }

        Log.d(
            "BookingActivity",
            "Received Resource: ${resource.name}, " +
                    "Rental days: $rentalDays"
        )

        setContent {
            BookingScreen(
                resource = resource,
                rentalDays = rentalDays,
                currentCredits = currentCredits,

                // Back button simply closes BookingActivity.
                onBack = {
                    Log.d(
                        "BookingActivity",
                        "Back button pressed. Closing BookingActivity."
                    )

                    finish()
                },

                // This callback is triggered when the user presses Review
                // after entering valid customer information.
                onReviewBooking = { name, email ->

                    // Create an Intent for the final confirmation Activity.
                    val confirmIntent = Intent(
                        this@BookingActivity,
                        ConfirmBooking::class.java
                    )

                    // Pass the customer's name to ConfirmBooking.
                    confirmIntent.putExtra(
                        "name",
                        name
                    )

                    // Pass the customer's email to ConfirmBooking.
                    confirmIntent.putExtra(
                        "email",
                        email
                    )

                    // Pass the selected Resource.
                    //
                    // Resource implements Parcelable, so Android can transfer
                    // the complete Resource object through the Intent.
                    confirmIntent.putExtra(
                        "resource",
                        resource
                    )

                    // Pass the rental period selected on the Browse screen.
                    confirmIntent.putExtra(
                        "rentalDays",
                        rentalDays
                    )

                    // remaining balance after the booking.
                    confirmIntent.putExtra(
                        "currentCredits",
                        currentCredits
                    )

                    Log.d(
                        "BookingActivity",
                        "Launching ConfirmBooking through Activity Result API. " +
                                "Name: $name, " +
                                "Resource: ${resource.name}, " +
                                "Rental days: $rentalDays"
                    )

                    // Launch ConfirmBooking and wait for its result.
                    confirmBookingLauncher.launch(confirmIntent)
                }
            )
        }
    }


        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun BookingScreen(
            resource: Resource, rentalDays: Int, currentCredits: Int, onBack: () -> Unit,
            onReviewBooking: (
                name: String,
                email: String
            ) -> Unit
        ) {
            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var nameError by remember { mutableStateOf(false) }
            var emailError by remember { mutableStateOf(false) }

            val context = LocalContext.current

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
                    modifier = Modifier
                        .padding(innerPadding)
                ) {

                    // Resource information
                    Column(
                        modifier = Modifier.padding(start = 140.dp)
                    ) {
                        Text(
                            text = "Booking Summary",
                            fontSize = 22.sp,
                            modifier = Modifier.padding(
                                top = 12.dp,
                                start = 0.dp
                            )
                        )

                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Bike and scooter rental logo",
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .height(120.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = resource.name,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        Text(
                            text = resource.category,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = resource.location,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = "⭐ ${resource.rating}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

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
                    // Customer details
                    Text(
                        text = "Customer Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 20.dp, start = 5.dp)
                    )
                    Text(
                        text = "Name",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 10.dp, start = 5.dp)
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = false
                        },
                        placeholder = {
                            Text("Enter your Name")
                        },
                        isError = nameError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(start = 5.dp, end = 16.dp, top = 5.dp)
                    )
                    if (nameError) {
                        Text(
                            text = "Name is required",
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 5.dp, top = 3.dp)
                        )
                    }
                    Text(
                        text = "Email",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 10.dp, start = 5.dp)
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = false
                        },
                        placeholder = {
                            Text("Enter your Email")
                        },
                        isError = emailError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(start = 5.dp, end = 16.dp, top = 5.dp)
                    )
                    if (emailError) {
                        Text(
                            text = "Email is required",
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 5.dp, top = 3.dp)
                        )
                    }
                    Text(
                        text = "Rental Period",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 15.dp, start = 5.dp)
                    )
                    Row(
                        modifier = Modifier.padding(top = 20.dp, start = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${resource.pricePerDay} Credits x $rentalDays day(s)",
                            fontSize = 22.sp,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(top = 3.dp, start = 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total: ${resource.pricePerDay * rentalDays} Credits",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 15.dp, start = 5.dp)
                        )
                        Text(
                            text = "Available: $currentCredits Credits",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 15.dp, start = 40.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 20.dp, start = 100.dp)
                    ) {
                        Button(
                            onClick = {
                                // Cancel action later
                            },
                            modifier = Modifier
                                .width(100.dp)
                                .height(50.dp)
                                .padding(start = 5.dp)
                        ) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                // Checking whether the required customer fields have been filled.
                                nameError = name.isBlank()
                                emailError = email.isBlank()

                                // Only continue if both required fields contain information.
                                if (!nameError && !emailError) {

                                    Log.d(
                                        "BookingActivity",
                                        "Review button pressed. " +
                                                "Customer details validated successfully."
                                    )

                                    // Ask BookingActivity to launch ConfirmBooking through
                                    // the Activity Result API.
                                    onReviewBooking(
                                        name,
                                        email
                                    )
                                } else {

                                    // At least one required field is empty, so the app stays
                                    // on the BookingActivity screen and show the errors.
                                    Log.d(
                                        "BookingActivity",
                                        "Review blocked. " +
                                                "Name error: $nameError, " +
                                                "Email error: $emailError"
                                    )
                                }
                            },
                            modifier = Modifier
                                .width(100.dp)
                                .height(50.dp)
                                .padding(start = 5.dp)
                        ) {
                            Text("Review")
                        }
                    }
                }
            }
        }
}

