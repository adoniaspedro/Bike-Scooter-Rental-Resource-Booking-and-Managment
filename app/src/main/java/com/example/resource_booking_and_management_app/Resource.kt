package com.example.resource_booking_and_management_app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Resource(
    // Unique identifier for this resource.
    val id: String,

    // Name of the bike or scooter.
    val name: String,

    // Type/category of the resource, e.g. Electric Bike or Electric Scooter.
    val category: String,

    // Year the bike/scooter was released or manufactured.
    val year: Int,

    // User rating out of 5.
    val rating: Double,

    // Current location of the resource.
    val location: String,

    // Cost to rent the resource for one day.
    val pricePerDay: Int,

    // Number of units currently available to book.
    val availability: Int,

    // Bike/scooter-specific attribute.
    // For bikes this represents the estimated range in kilometres.
    val rangeKm: Int,

    // Short description of the resource.
    val description: String
) : Parcelable