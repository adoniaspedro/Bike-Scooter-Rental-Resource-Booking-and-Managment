package com.example.resource_booking_and_management_app

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    // ViewModel cleanup
    override fun onCleared() {
        super.onCleared()

        Log.d(
            TAG,
            "BookingViewModel cleared."
        )
    }
}