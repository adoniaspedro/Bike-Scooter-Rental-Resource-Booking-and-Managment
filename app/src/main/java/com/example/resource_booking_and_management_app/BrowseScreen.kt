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

import androidx.compose.runtime.collectAsState

// Import for Logging
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(viewModel: BookingViewModel, modifier: Modifier = Modifier) {

    var searchText by remember { mutableStateOf("") }
    var selectedSort by remember { mutableStateOf("Rating") }
    var sortExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val resources by viewModel.resources.collectAsState()

    // Keeps track of which resource is currently being displayed.
    // 0 = first resource, 1 = second resource, etc.
    var currentResourceIndex by remember { mutableStateOf(0) }

    // Filter resources based on the search text in the search bar
    val filteredResources = if (searchText.isBlank()) {
        resources
    } else {
        resources.filter { resource ->
            resource.name.contains(searchText, ignoreCase = true) ||
                    resource.category.contains(searchText, ignoreCase = true) ||
                    resource.location.contains(searchText, ignoreCase = true) ||
                    resource.description.contains(searchText, ignoreCase = true)
        }
    }

    // Sort the filtered resources based on the option selected
    // in the Sort dropdown.
    val sortedResources = when (selectedSort) {

        // Highest rating first
        "Rating" -> {
            filteredResources.sortedByDescending { it.rating }
        }

        // Lowest price first
        "Price (Low → High)" -> {
            filteredResources.sortedBy { it.pricePerDay }
        }

        // Highest price first
        "Price (High → Low)" -> {
            filteredResources.sortedByDescending { it.pricePerDay }
        }

        // Alphabetical order by resource name
        "Name (A → Z)" -> {
            filteredResources.sortedBy { it.name }
        }

        // Fallback in case no recognised sort option is selected
        else -> {
            filteredResources
        }
    }

    // Get the resource at the current index.
    val currentResource = sortedResources.getOrNull(currentResourceIndex)

    // Debugging log to confirm that BrowseScreen is receiving
    // the resource collection from BookingViewModel.
    Log.d(
        "BrowseScreen",
        "Displaying resource index $currentResourceIndex of ${resources.size}"
    )

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

                            // Reset to the first search result whenever
                            // the search query changes.
                            currentResourceIndex = 0

                            Log.d(
                                "BrowseScreen",
                                "Search text changed to '$it'. Resetting resource index to 0."
                            )
                        },
                        onSearch = {
                            Log.d(
                                "BrowseScreen",
                                "Search submitted: '$searchText'. " +
                                        "Found ${filteredResources.size} matching resources."
                            )
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
                currentResource?.let { resource ->
                    Text(
                        text = resource.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 115.dp)
                    )

                    Text(
                        text = resource.category,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 4.dp, start = 115.dp)
                    )

                    Text(
                        text = "⭐ ${resource.rating}",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp, start = 115.dp)
                    )

                    Text(
                        text = resource.year.toString(),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 4.dp, start = 115.dp)
                    )

                    Text(
                        text = resource.location,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 4.dp, start = 115.dp)
                    )

                    Text(
                        text = "${resource.pricePerDay} credits / day",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, start = 115.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = {
                            // Only move backwards if not already
                            // displaying the first resource.
                            if (currentResourceIndex > 0) {

                                currentResourceIndex--

                                Log.d(
                                    "BrowseScreen",
                                    "Previous resource selected. New index: $currentResourceIndex"
                                )
                            }else {

                                Log.d(
                                    "BrowseScreen",
                                    "Previous Pressed!"
                                )
                            }
                        }
                    ) {
                        Text(
                            text = "< Previous",
                            fontSize = 18.sp
                        )

                    }

                    TextButton(
                        onClick = {
                            // Only move forward if not already
                            // displaying the final resource.
                            if (currentResourceIndex < sortedResources.lastIndex) {

                                currentResourceIndex++

                                Log.d(
                                    "BrowseScreen",
                                    "Next resource selected. New index: $currentResourceIndex"
                                )
                            } else {

                                Log.d(
                                    "BrowseScreen",
                                    "Next pressed!"
                                )
                            }
                        },
                        modifier = Modifier.padding(start = 20.dp)
                    ) {
                        Text(
                            text = "Next >",
                            fontSize = 18.sp
                        )

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
                            val intent = Intent(context, BookingBasket::class.java)
                            context.startActivity(intent)
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