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

    init {
        Log.d(
            TAG,
            "BookingViewModel created. Loaded ${_resources.value.size} resources."
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