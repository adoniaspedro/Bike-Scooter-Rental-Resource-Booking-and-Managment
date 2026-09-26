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

class BookingHistory : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BookingHistoryScreen(
                onBack = {
                    finish()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryScreen(onBack: () -> Unit) {

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
                text = "Booking History",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 110.dp
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp, start = 5.dp, end = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "City Cruiser Electric Bike",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Melbourne CBD",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )

                    Text(
                        text = "Rental Period: 2 days",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 15.dp)
                    )

                    Text(
                        text = "Price: 45 credits/day",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )

                    Text(
                        text = "Total: 90 Credits",
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
                                // Cancel booking
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
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 5.dp, end = 16.dp)
            )   {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "City Explorer Electric Scooter",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Melbourne CBD",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )

                    Text(
                        text = "Rental Period: 3 days",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 15.dp)
                    )

                    Text(
                        text = "Price: 35 credits/day",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )

                    Text(
                        text = "Total: 105 Credits",
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
                                // Cancel booking
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
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 210.dp)
                    .height(2.dp)
            )  {
                drawLine(
                    color = Color.Black,
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                    strokeWidth = 5f,
                    pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                        floatArrayOf(10f, 10f)
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = {
                        // Browse resource
                    }
                ) {
                    Text("Browse")
                }

                TextButton(
                    onClick = {
                        // Basket resource
                    }
                ) {
                    Text("Basket")
                }
        }
    }
}}
