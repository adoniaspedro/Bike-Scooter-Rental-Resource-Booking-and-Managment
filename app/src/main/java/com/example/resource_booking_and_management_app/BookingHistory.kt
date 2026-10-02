package com.example.resource_booking_and_management_app

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

//Imports for the cards
import androidx.compose.material3.Card

//Imports for the buttons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

//Font Imports
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Import for Logging
import android.util.Log

//Imports for Lazy Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

//Imports for Snackbarhost
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(viewModel: BookingViewModel, onBack: () -> Unit,
                         onBrowseClick: () -> Unit, onBasketClick: () -> Unit) {

    // Controls the Snackbar message shown after
    // a booking has been successfully cancelled.
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    // Used to launch the Snackbar coroutine.
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(bottom = 100.dp)
            )
        },
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
                        .fillMaxWidth()
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
                                                // Show a confirmation message after the booking
                                                // has been successfully cancelled.
                                                coroutineScope.launch {
                                                    snackbarHostState.showSnackbar(
                                                        message = "Cancelled"
                                                    )
                                                }

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 10.dp,
                            bottom = 10.dp
                        ),
                    horizontalArrangement = Arrangement.Center
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