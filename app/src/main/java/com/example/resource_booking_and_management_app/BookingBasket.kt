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
import android.util.Log
import androidx.compose.ui.unit.dp

//Imports for sending resources
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

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

//Imports for the dotted horizontal line
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color

//Imports for Snackbar pop message
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.resource_booking_and_management_app.ui.theme.Purple40
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingBasketScreen(viewModel: BookingViewModel, onBack: () -> Unit,
                        onBrowseClick: () -> Unit, onBookNow: () -> Unit, onHistoryClick: () -> Unit) {

    val basketResource by viewModel.basketResource.collectAsState()
    val basketDays by viewModel.basketDays.collectAsState()
    val credits by viewModel.credits.collectAsState()
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .padding(bottom = 70.dp)
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
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = "Booking Basket",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 110.dp
                )
            )
            if (basketResource == null) {

                Text(
                    text = "Your basket is empty. Add a bike or scooter from Browse.",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(
                        top = 30.dp,
                        start = 40.dp,
                        end = 20.dp
                    )
                )

            } else {

                val resource = basketResource!!
                val total = resource.pricePerDay * basketDays
                val remainingCredits = credits - total

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
                        Text(
                            text = "${resource.name} ${resource.category}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = resource.location,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 5.dp)
                        )

                        Text(
                            text = "Rental Period: $basketDays day(s)",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 15.dp)
                        )

                        Text(
                            text = "Price: ${resource.pricePerDay} credits/day",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 5.dp)
                        )

                        Text(
                            text = "Total: $total Credits",
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
                                    viewModel.removeFromBasket()

                                    Log.d(
                                        "BookingBasket",
                                        "Remove button pressed. Basket resource removed."
                                    )
                                },
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(40.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red
                                )
                            ) {
                                Text(
                                    text = "Remove",
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(
                        top = 20.dp,
                        start = 100.dp
                    )
                ) {
                    Text(
                        text = "Total: $total Credits",
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Your Credits: $credits Credits",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "Remaining After Booking: $remainingCredits Credits",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 0.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {

                            // Check the 400 credit booking limit before
                            // launching BookingActivity.
                            if (total > 400) {

                                Log.d(
                                    "BookingBasket",
                                    "Book Now rejected. " +
                                            "Total cost of $total credits exceeds the 400 credit limit."
                                )

                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "A booking cannot exceed 400 credits."
                                    )
                                }

                            } else {

                                // The booking is within the 400-credit limit,
                                // so BookingActivity can be launched.
                                Log.d(
                                    "BookingBasket",
                                    "Book Now accepted. " +
                                            "Total cost: $total credits. " +
                                            "Requesting BookingActivity launch."
                                )

                                onBookNow()
                            }
                        },
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Text("Book Now")
                    }
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 150.dp)
                        .height(2.dp)
                ) {
                    drawLine(
                        color = Purple40,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                        strokeWidth = 10f,
                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                            floatArrayOf(10f, 10f)
                        )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        onBrowseClick()

                        Log.d(
                            "BookingBasket",
                            "Browse button pressed. Returning to Browse screen."
                        )
                    }
                ) {
                    Text("Browse")
                }

                TextButton(
                    onClick = {
                        Log.d(
                            "BookingBasket",
                            "History button clicked. Navigating to Booking History."
                        )
                        onHistoryClick()
                    },
                    modifier = Modifier.padding(start = 30.dp)
                ) {
                    Text("History")
                }
            }
        }

    }
}