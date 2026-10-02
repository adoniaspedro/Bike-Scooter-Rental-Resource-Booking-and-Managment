package com.example.resource_booking_and_management_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment

//Imports for the cards
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.padding

//Imports for the buttons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

//Font Imports
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width

//Imports for the dotted horizontal line
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Import for Logging
import android.util.Log

// Imports for scrolling
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

//Imports for Lazy Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(viewModel: BookingViewModel, onBack: () -> Unit,
                         onBrowseClick: () -> Unit, onBasketClick: () -> Unit) {
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
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val bookingHistory by viewModel.bookingHistory.collectAsState()
            //val scrollState = rememberScrollState()

            Text(
                text = "Booking History",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 110.dp)
            )
            if (bookingHistory.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 100.dp,
                            start = 20.dp,
                            end = 20.dp
                        ),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No Active Bookings.",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Your bookings will appear here",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "after you confirm a reservation.",
                        fontSize = 16.sp
                    )
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(bookingHistory) { booking ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 25.dp,
                                    start = 5.dp,
                                    end = 16.dp
                                )
                        ) {
                            Column(
                                modifier = Modifier.padding(15.dp)
                            ) {

                                // Booking ID is displayed at the top of the card.
                                Text(
                                    text = booking.id,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = booking.resource.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 10.dp)
                                )

                                Text(
                                    text = booking.resource.category,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )

                                Text(
                                    text = booking.resource.location,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )

                                Text(
                                    text = "Customer: ${booking.customerName}",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 15.dp)
                                )

                                Text(
                                    text = "Rental Period: ${booking.rentalDays} days",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )

                                Text(
                                    text = "Price: ${booking.resource.pricePerDay} credits/day",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )

                                Text(
                                    text = "Total: ${booking.totalCredits} Credits",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 5.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = {

                                            Log.d(
                                                "BookingHistory",
                                                "Cancel button clicked for booking: ${booking.id}"
                                            )

                                            val cancelled = viewModel.cancelBooking(booking.id)

                                            if (cancelled) {

                                                Log.d(
                                                    "BookingHistory",
                                                    "Booking ${booking.id} cancelled successfully."
                                                )

                                            } else {

                                                Log.d(
                                                    "BookingHistory",
                                                    "Failed to cancel booking ${booking.id}."
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(40.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Red
                                        )
                                    ) {
                                        Text("Cancel")
                                    }
                                }
                            }
                        }
                    }
                }
                /*Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 210.dp)
                        .height(2.dp)
                ) {
                    drawLine(
                        color = Color.Black,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                        strokeWidth = 5f,
                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                            floatArrayOf(10f, 10f)
                        )
                    )
                }*/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = onBrowseClick
                    ) {
                        Text("Browse")
                    }

                    TextButton(
                        onClick = onBasketClick
                    ) {
                        Text("Basket")
                    }
                }
            }
        }
    }
}
