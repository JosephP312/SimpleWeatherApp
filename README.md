# Simple Weather App

**Simple Weather App** is a clean, user-friendly Android mobile application designed to help individuals quickly access current weather conditions, forecasts, and severe weather alerts for any city. The app automatically fetches real-time weather data and provides personalized outfit recommendations based on current conditions — giving users exactly what they need without the clutter or ads found in competing apps.

---

## Overview

Most weather apps overwhelm users with information they never asked for and bombard them with intrusive advertisements. Simple Weather App solves this by presenting only the most relevant weather information in a clean, easy-to-read format. The app also includes a unique outfit recommendation engine that translates raw weather data into practical daily advice.

---

## Key Features

- **Real-Time Weather:** Automatically detects the user's GPS location and loads current conditions on launch.
- **5-Day Forecast:** Displays a horizontal scrollable forecast strip with daily highs and lows.
- **My Cities:** Save, manage, and organize multiple city locations with swipe-to-delete and long-press to set default.
- **Severe Weather Alerts:** Push notifications delivered via Firebase Cloud Messaging for dangerous conditions.
- **Offline-First Design:** Caches the last fetched weather data locally using Room so the app works without internet.
- **Dark Mode Support:** Full light and dark theme with adaptive colors across all screens.

---

## Tech Stack

### Frontend
- **Language:** Kotlin
- **UI Framework:** XML Layouts with ViewBinding
- **Architecture:** MVVM (Model-View-ViewModel) with LiveData and Coroutines
- **Navigation:** Jetpack Navigation Component with Bottom Navigation Bar
- **Image Loading:** Glide for weather condition icons

### Backend (Google Firebase Ecosystem)
- **Authentication:** Firebase Auth (Email/Password and Google Sign-In)
- **Database:** Cloud Firestore for saved city profiles and user preferences
- **Notifications:** Firebase Cloud Messaging (FCM) for severe weather push alerts
- **Local Cache:** Room Database for offline-first weather storage

---

## Getting Started

### Prerequisites
- Android Studio (latest stable version)
- Android device or emulator running API 26 (Android 8.0) or higher
- A free OpenWeatherMap API key (openweathermap.org)
- A Firebase project (you will need to add your own `google-services.json`)

### Installation
1. **Clone the repository:**
   ```
   git clone https://github.com/JosephP312/SimpleWeatherApp.git
   ```
2. **Open in Android Studio:** Select File > Open and navigate to the cloned directory.
3. **Add API Key:** Open `local.properties` and add `WEATHER_API_KEY=your_key_here`
4. **Add Firebase Config:** Place your `google-services.json` file inside the `/app` folder.
5. **Build & Run:** Sync Gradle and click the Run button to launch on an emulator or physical device.

---
## Project Outline

[Project Outline](https://github.com/JosephP312/SimpleWeatherApp/wiki/App-Outline)
