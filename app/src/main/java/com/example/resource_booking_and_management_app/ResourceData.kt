package com.example.resource_booking_and_management_app

import com.example.resource_booking_and_management_app.R


val resourceList = listOf(

    Resource(
        id = "BIKE001",
        name = "City Cruiser",
        category = "Electric Bike",
        year = 2026,
        rating = 4.7,
        location = "Melbourne CBD",
        pricePerDay = 58,
        availability = 6,
        rangeKm = 80,
        description = "A comfortable electric bike designed for city travel.",
        imageResId = R.drawable.city_cruiser

    ),

    Resource(
        id = "SCOOTER001",
        name = "City Explorer",
        category = "Electric Scooter",
        year = 2025,
        rating = 4.5,
        location = "Box Hill",
        pricePerDay = 35,
        availability = 8,
        rangeKm = 50,
        description = "A lightweight electric scooter suitable for short city trips.",
        imageResId = R.drawable.city_explorer
    ),

    Resource(
        id = "BIKE002",
        name = "Mountain Pro",
        category = "Mountain Bike",
        year = 2024,
        rating = 4.8,
        location = "Richmond",
        pricePerDay = 40,
        availability = 4,
        rangeKm = 0,
        description = "A durable mountain bike designed for trails and off-road riding.",
        imageResId = R.drawable.mountain_pro
    ),

    Resource(
        id = "SCOOTER002",
        name = "Urban Glide",
        category = "Electric Scooter",
        year = 2022,
        rating = 4.3,
        location = "Southbank",
        pricePerDay = 30,
        availability = 5,
        rangeKm = 45,
        description = "A compact scooter designed for convenient urban transportation.",
        imageResId = R.drawable.urban_glide
    ),

    Resource(
        id = "BIKE003",
        name = "Road Runner",
        category = "Road Bike",
        year = 2023,
        rating = 4.6,
        location = "Fitzroy",
        pricePerDay = 38,
        availability = 3,
        rangeKm = 0,
        description = "A lightweight road bike designed for longer rides and cycling routes.",
        imageResId = R.drawable.road_runner
    ),

    Resource(
        id = "SCOOTER003",
        name = "Metro Swift",
        category = "Electric Scooter",
        year = 2026,
        rating = 4.6,
        location = "Docklands",
        pricePerDay = 60,
        availability = 7,
        rangeKm = 55,
        description = "A fast and reliable electric scooter designed for everyday city travel.",
        imageResId = R.drawable.metro_swift
    )
)