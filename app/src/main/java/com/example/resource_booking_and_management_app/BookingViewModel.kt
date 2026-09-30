package com.example.resource_booking_and_management_app

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Represents one completed booking.
// Stores the information needed for the booking history screen
// and for cancelling/refunding a booking later.
data class Booking(
    val id: String,
    val customerName: String,
    val customerEmail: String,
    val resource: Resource,
    val rentalDays: Int,
    val totalCredits: Int
)

class BookingViewModel : ViewModel() {

    companion object {
        private const val TAG = "BookingViewModel"
    }

    //The currently available resources.
    //MutableStateFlow allows the UI to automatically react when
    //the resource state changes.
    private val _resources = MutableStateFlow(resourceList)

    val resources: StateFlow<List<Resource>> = _resources.asStateFlow()

    //The resource currently stored in the booking basket.
    //The basket can contain only one unique resource at a time.
    private val _basketResource = MutableStateFlow<Resource?>(null)

    val basketResource: StateFlow<Resource?> = _basketResource.asStateFlow()


    //Number of days/booking units selected for the basket resource.
    private val _basketDays = MutableStateFlow(0)

    val basketDays: StateFlow<Int> = _basketDays.asStateFlow()

    //User's current credit balance.
    //Starting with 500 credits.
    private val _credits = MutableStateFlow(500)

    val credits: StateFlow<Int> = _credits.asStateFlow()

    init {
        Log.d(
            TAG,
            "BookingViewModel created. Loaded ${_resources.value.size} resources."
        )
    }

    // Stores all successfully completed bookings.
    // The Booking History screen will observe this StateFlow,
    // so the UI will automatically update when a new booking is added.
    private val _bookingHistory =
        MutableStateFlow<List<Booking>>(emptyList())

    val bookingHistory: StateFlow<List<Booking>> =
        _bookingHistory.asStateFlow()


    // Function for adding a resource to the booking basket.
    fun addToBasket(resource: Resource, days: Int) {

        // Store the selected resource in the basket.
        _basketResource.value = resource

        // Store the selected rental period.
        _basketDays.value = days

        Log.d(
            TAG,
            "Resource added to basket: ${resource.name}, " +
                    "Rental period: $days days."
        )
    }


     // Function that removes the current resource from the booking basket.
    fun removeFromBasket() {

        // Remove the resource from the basket.
        _basketResource.value = null

        // Reset the rental period.
        _basketDays.value = 0

        Log.d(
            TAG,
            "Basket cleared. Resource removed from basket."
        )
    }

    // Returns true when the booking was successfully saved.
    // Returns false when the booking could not be completed.
    fun confirmBooking(
        customerName: String,
        customerEmail: String,
        resource: Resource,
        rentalDays: Int,
        totalCredits: Int
    ): Boolean {

        Log.d(
            TAG,
            "Attempting to confirm booking for ${resource.name}. " +
                    "Customer: $customerName, " +
                    "Rental days: $rentalDays, " +
                    "Total: $totalCredits credits."
        )

        // Make=ing sure the booking has at least one rental day.
        if (rentalDays < 1) {
            Log.d(
                TAG,
                "Booking rejected. Rental period must be at least 1 day."
            )
            return false
        }

        // Making sure a single booking cannot cost more than 400 credits.
        if (totalCredits > 400) {
            Log.d(
                TAG,
                "Booking rejected. " +
                        "Total cost of $totalCredits exceeds the 400 credit limit."
            )
            return false
        }

        // Making sure the user has enough credits.
        if (totalCredits > _credits.value) {
            Log.d(
                TAG,
                "Booking rejected. " +
                        "Required: $totalCredits credits. " +
                        "Available: ${_credits.value} credits."
            )
            return false
        }

        // Find the latest version of the selected resource.
        // This is important because its availability may have changed
        // since it was originally added to the basket.
        val currentResource = _resources.value.find {
            it.id == resource.id
        }

        // Making sure the resource still exists.
        if (currentResource == null) {
            Log.d(
                TAG,
                "Booking rejected. Resource ${resource.id} no longer exists."
            )
            return false
        }

        // Make sure at least one unit of the resource is available.
        if (currentResource.availability <= 0) {
            Log.d(
                TAG,
                "Booking rejected. " +
                        "${currentResource.name} is no longer available."
            )
            return false
        }

        // Reduce the user's credit balance.
        _credits.value -= totalCredits

        Log.d(
            TAG,
            "Credits deducted: $totalCredits. " +
                    "Remaining credits: ${_credits.value}"
        )

        // Reduce the resource availability by one.
        // Rental days represent how long the resource is booked,
        // while availability represents how many physical units exist.
        val updatedResources = _resources.value.map {
            if (it.id == currentResource.id) {
                it.copy(
                    availability = it.availability - 1
                )

            } else {
                it
            }
        }

        _resources.value = updatedResources

        Log.d(
            TAG,
            "Resource availability updated for ${currentResource.name}. " +
                    "Remaining availability: ${currentResource.availability - 1}"
        )

        // Create a unique ID for this booking.
        val bookingId = "BOOKING-${System.currentTimeMillis()}"

        // Create the completed booking record.
        val newBooking = Booking(
            id = bookingId,
            customerName = customerName,
            customerEmail = customerEmail,
            resource = currentResource,
            rentalDays = rentalDays,
            totalCredits = totalCredits
        )

        // Add the completed booking to Booking History.
        _bookingHistory.value = _bookingHistory.value + newBooking

        Log.d(
            TAG,
            "Booking saved successfully. " +
                    "Booking ID: $bookingId, " +
                    "Resource: ${currentResource.name}"
        )

        removeFromBasket()

        Log.d(
            TAG,
            "Booking process completed successfully."
        )
        return true
    }

    // ViewModel cleanup
    override fun onCleared() {
        super.onCleared()

        Log.d(
            TAG,
            "BookingViewModel cleared."
        )
    }
}