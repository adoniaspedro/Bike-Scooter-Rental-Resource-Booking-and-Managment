# Bike/Scooter Rental: Resource Booking and Management

Android application for browsing, booking and managing bike and scooter rentals. You spend credits to rent a bike or scooter, review your booking before confirming it, and can cancel a booking later for a full refund. Built with Kotlin and Jetpack Compose (Material 3), using Navigation Compose and a shared `ViewModel`.

---

## Features
- Browse 6 bikes and scooters, one at a time, with image, rating, year, location and price
- Search by name, category, location or description
- Sort by rating, price (low to high or high to low) or name (A to Z)
- Choose a rental period of 1 to 7 days
- Booking basket with a cost summary
- Customer details form with validation, followed by a confirmation screen
- Credit balance that starts at 500 and updates with every booking or cancellation
- Booking history with cancel and refund
- Light and Dark themes

---
## Getting Started

### Requirements
- Android Studio (the preferred IDE with the recent version)
- Android SDK 24 or higher (Android 7.0+) on the device or emulator
- An internet connection for the first Gradle sync

### Run the app
1. Clone the repository:
   ```
   git clone https://github.com/adoniaspedro/Bike-Scooter-Rental-Resource-Booking-and-Managment.git
   ```
2. Open the project in Android Studio and wait for Gradle to finish syncing.
3. Select an emulator or connect a physical device.
4. Press **Run** (the green play button).

---

## How to Use

### 1. Browse screen (home)
The app opens on the Browse screen. The top bar shows the app title, your current credit balance (e.g. **500 Credits**) and a theme button.

| Control | What it does |
|---|---|
| **Search bar** | Type to filter by name, category, location or description (e.g. "scooter", "Richmond") |
| **Sort** | Choose Rating, Price (Low → High), Price (High → Low) or Name (A → Z) |
| **Rental Period** | Choose 1 to 7 days |
| **< Previous / Next >** | Move through the available bikes and scooters |
| **Add to Basket** | Puts the shown bike or scooter in your basket for the selected number of days. A "Added to Basket" message confirms it. |
| **☾ Dark / ☀ Light** | Switch between Dark and Light theme |
| **Browse / Basket / History** | Bottom navigation between screens |

Each card shows the name, category, rating, year, location and price per day in credits.

### 2. Booking Basket
Open the **Basket** tab to see your selected bike or scooter, rental period, price per day and total. It also shows your credit balance and what you'll have left after booking.

- The basket holds **one bike or scooter at a time**. Adding another replaces it.
- **Remove** clears the basket.
- **Book Now** starts the booking. A single booking **cannot exceed 400 credits**, and you'll see a message if it does.

### 3. Booking Summary and customer details
After **Book Now**, enter your details:

1. Enter your **Name** (required).
2. Enter your **Email** (required).
3. Check the cost line (price × days), the total and your available credits.
4. Tap **Review** to continue, or **Cancel** to go back to the basket.

If a field is empty, an error is shown and you can't continue.

### 4. Confirm Booking
Review the resource, location, customer details, rental period, price, total credits and remaining credits after booking.

- **Confirm & Save** completes the booking. If you don't have enough credits, you'll see "You don't have enough Credits".
- **Cancel** returns you to the previous screen without booking.

On success, the credits are deducted, the available units of that bike or scooter drop by one, the booking is added to your history, and the basket is cleared.

### 5. Booking History
Open the **History** tab to see all confirmed bookings, each with its booking ID, bike or scooter, location, customer, rental period, price per day and total.

- **Cancel** removes the booking, refunds the full credits and makes that unit available again.
- With no bookings, the screen shows "No Active Bookings."

---

## Booking Rules

| Rule | Value |
|---|---|
| Starting credits | 500 |
| Rental period | 1 to 7 days |
| Maximum cost per booking | 400 credits |
| Basket capacity | 1 bike or scooter |
| Cancellation | Full refund, unit becomes available again |

---

## Available Bikes and Scooters

| Name | Type | Location | Credits / day | Units |
|---|---|---|---|---|
| City Cruiser | Electric Bike | Melbourne CBD | 58 | 6 |
| City Explorer | Electric Scooter | Box Hill | 35 | 8 |
| Mountain Pro | Mountain Bike | Richmond | 40 | 4 |
| Urban Glide | Electric Scooter | Southbank | 30 | 5 |
| Road Runner | Road Bike | Fitzroy | 38 | 3 |
| Metro Swift | Electric Scooter | Docklands | 60 | 7 |

---

## Tech Stack

- Kotlin
- Jetpack Compose with Material 3
- Navigation Compose
- ViewModel with StateFlow
- Activity Result API and Parcelable for passing data between screens
- Min SDK 24, Target SDK 37
