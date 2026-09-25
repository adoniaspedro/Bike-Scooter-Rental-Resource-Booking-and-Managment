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

class BookingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingScreen(
                onBack = {
                    finish()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(onBack: () -> Unit) {
    var name by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }

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
                        start = 0.dp)
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
                    text = "City Cruiser",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp)
                )

                Text(
                    text = "Electric Bike",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = "Melbourne CBD",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = "⭐ 4.7",
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
                onValueChange = { name = it },
                placeholder = {
                    Text("Enter your Name")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 5.dp, end = 16.dp, top = 5.dp)
            )
            Text(
                text = "Email",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 10.dp, start = 5.dp)
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text("Enter your Email")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 5.dp, end = 16.dp, top = 5.dp)
            )
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
                    text = "How many Days?",
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = 15.dp, top = 5.dp)
                )

                OutlinedTextField(
                    value = days,
                    onValueChange = { days = it },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 15.sp
                    ),
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(55.dp)
                        .height(50.dp)
                )
                Text(
                    text = "45 Credits x 1 day",
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(top = 3.dp, start = 10.dp)
                )
            }
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: 90 Credits",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 15.dp, start = 5.dp)
                )
                Text(
                    text = "Available: 500 Credits",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 15.dp, start = 40.dp)
                )
            }
            Row(
                modifier = Modifier.padding(top = 20.dp, start = 50.dp)
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
                        val intent = Intent(context, ConfirmBooking::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                        .padding(start = 5.dp)
                ) {
                    Text("Review")
                }
            }
        }
    }
}