package com.example.resource_booking_and_management_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextButton
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(modifier: Modifier = Modifier) {

    var searchText by remember { mutableStateOf("") }
    var selectedSort by remember { mutableStateOf("Rating") }
    var sortExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,

        topBar = {
            TopAppBar(
                title = {
                    Text("Bike/Scooter Rental")
                },
                actions = {
                    Text(
                        text = "500 Credits",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // Search bar
            SearchBar(
                state = rememberSearchBarState(),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchText,
                        onQueryChange = {
                            searchText = it
                        },
                        onSearch = {
                            // Search functionality will be added later
                        },
                        expanded = false,
                        onExpandedChange = {},
                        placeholder = {
                            Text("Search Bikes & Scooters...")
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            // Sort row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Text("Sort:")

                androidx.compose.material3.TextButton(
                    onClick = {
                        sortExpanded = true
                    }
                ) {
                    Text("$selectedSort ▼")
                }

                DropdownMenu(
                    expanded = sortExpanded,
                    onDismissRequest = {
                        sortExpanded = false
                    }
                ) {

                    DropdownMenuItem(
                        text = { Text("Rating") },
                        onClick = {
                            selectedSort = "Rating"
                            sortExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Price (Low → High)") },
                        onClick = {
                            selectedSort = "Price (Low → High)"
                            sortExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Price (High → Low)") },
                        onClick = {
                            selectedSort = "Price (High → Low)"
                            sortExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("Name (A → Z)") },
                        onClick = {
                            selectedSort = "Name (A → Z)"
                            sortExpanded = false
                        }
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Bike and scooter rental logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text(
                    text = "City Cruiser",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Electric Bike",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = "⭐ 4.7",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "2025",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = "Melbourne CBD",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = "45 credits / day",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            // Previous resource
                        }
                    ) {
                        Text("< Previous")
                    }

                    TextButton(
                        onClick = {
                            // Next resource
                        }
                    ) {
                        Text("Next >")
                    }
                }
                Button(
                    onClick = {
                        val intent = Intent(context, BookingActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(
                            start = 140.dp,
                            top = 10.dp
                        )
                ) {
                    Text("Booking")
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

                    TextButton(
                        onClick = {
                            val intent = Intent(context, BookingHistory::class.java)
                            context.startActivity(intent)
                        }
                    ) {
                        Text("History")
                    }
                }
            }
        }
    }
}