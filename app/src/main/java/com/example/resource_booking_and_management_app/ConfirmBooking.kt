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

        setContent {
            ConfirmBookingScreen(
                onBack = {
                    finish()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmBookingScreen(onBack: () -> Unit) {
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

            Text(
                text = "City Cruiser",
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 8.dp, start = 5.dp)
            )

            Text(
                text = "Electric Bike",
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 4.dp, start = 5.dp)
            )

            Text(
                text = "Location",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, start = 5.dp)
            )

            Text(
                text = "Melbourne CBD",
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 8.dp, start = 5.dp)
            )

            Text(
                text = "Customer",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, start = 5.dp)
            )

            Text(
                text = "Dorris Parton",
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 8.dp, start = 5.dp)
            )

            Text(
                text = "dorris@email.com",
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 4.dp, start = 5.dp)
            )

            Text(
                text = "Rental Period",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, start = 5.dp)
            )

            Text(
                text = "2 days",
                fontSize = 17.sp,
                modifier = Modifier.padding(top = 8.dp, start = 5.dp)
            )

            Text(
                text = "Price",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp, start = 5.dp)
            )

            Text(
                text = "45 credits / day",
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
            Text(
                text = "Total Credits: 90 Credits",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 30.dp, start = 5.dp)
            )
            Text(
                text = "Remaining Credits After Booking: 410 Credits",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 20.dp, start = 5.dp)
            )
            Row(
                modifier = Modifier.padding(top = 120.dp, start = 50.dp)
            ) {
                Button(
                    onClick = {
                        // Cancel action later
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
                        // Click
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