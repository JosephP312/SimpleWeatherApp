# Simple Weather App — Formal Outline

**Prepared by:** Joseph Prignano
**Course:** COM-437
**Date:** May 3, 2026
**GitHub:** https://github.com/JosephP312/SimpleWeatherApp

---

## I. Project Description

**A. Overview**
Simple Weather App is a clean, user-friendly Android mobile application to help individuals quickly access current weather conditions, forecasts, and severe weather alerts for cities they have saved and in the current location of the user. The core value of the application is to automatically give the user the information they need to be aware of the current or incoming conditions to their location.

**B. Target Audience**
The primary target audience for Simple Weather App is anyone who needs to access easy-to-read weather information without the issues of ads or other information they might not need on the screen. This can be anyone from a student who needs to know the weather for class, to someone who is traveling, or someone who wants to hike and ensure they bring the right gear and anyone in between.

**C. Project Scope**
The following are the areas we focused on for this application:

- City search and city management — users can add or delete cities as they please
- 5-day forecast showing highs and lows in a quick and informative manner
- GPS location services so the user can access weather upon opening the app
- Push notifications for severe weather alerts or updates
- User account management

---

## II. Problem Addressing

**A. Market Context**
The weather application market is valued around $2.5 billion according to a 2024 report, with an expected growth rate of about 11.2 percent. There are over 4 billion users worldwide who rely on weather forecasts for their day-to-day activities. While the market is large and the user base is growing, there are significant negatives within already established apps that users are not happy with:

- **Information overload** — applications present far more data than users want or need
- **Poor Interface Design** — cluttered layouts force users to search for basic information
- **Monetization** — applications are saturated with ads that users find disruptive

**B. Solution Impact**
Simple Weather App plans to directly address these issues by incorporating solutions into the design elements during the developmental cycle. The app will only provide information briefly and clearly for the user. The app will be focused on being free without ads and any location data attached to the user is properly handled and never stored on external servers. We want to stand out in an already crowded market so users feel in control of their choices.

---

## III. Platform

**A. Development Environment**
The app was developed in Android Studio with the application written entirely in Kotlin. Components are maintained by Google and JetBrains.

**B. Deployment and Ecosystem**
The application is intended for distribution through Google Play Store. All operational services remain within Google's ecosystem to ensure seamless integration and compliance.

**C. Target Devices**
The app is designed to run on Android 8.0 (API 26) and above, allowing compatibility across a wide range of devices including those not running the latest hardware or software.

| Specification | Value |
|---|---|
| Language | Kotlin |
| IDE | Android Studio |
| Distribution | Google Play Store |

---

## IV. Front/Back End Support

**A. Frontend Architecture**
The frontend is a native Android application built within Android Studio. The application follows the MVVM architecture pattern which provides a clean separation between the UI and the logic that runs the app.

**B. Backend Architecture**
The backend platform resides within Google Firebase, selected for its seamless integration with the Android ecosystem and its comprehensive free tier that covered everything needed without requiring a custom server infrastructure.

**C. External API Integration**
Weather data is sourced from the OpenWeatherMap API, which provides current conditions and a 5-day forecast. The free tier allows up to one million API calls per month which is sufficient for the current development phase.

**D. Local Data Layer**
If a user is unable to connect to the internet, the app will cache information for selected cities using Room Database until a fresh fetch can be completed, so users always have access to recent weather data.

**E. Security and Privacy**
All data transmitted within the app is encrypted following Firebase standards. The OpenWeatherMap API key is stored in local.properties and accessed at build time, never committed to version control. Location data is used only during the active app session and is never transmitted to any external party beyond OpenWeatherMap.

---

## V. Functionality

**A. Navigation**
Simple Weather App uses a single-activity architecture with a Bottom Navigation Bar giving users access to three tabs at any time — Home, My Cities, and Settings.

**B. Home Screen**
The home screen loads on launch and displays current weather for the user's detected location. It shows current temperature, high and low temperatures, humidity, wind speed, and a 5-day forecast strip. Users can pull down from the top to refresh and fetch the latest weather data.

**C. My Cities**
The second tab allows users to view weather for a list of saved locations. Users can add cities by searching, set favorites to appear at the top, and swipe to delete cities they no longer need. If no city has been added the page displays an empty state.

**D. Settings**
The Settings tab allows users to manage their account and application preferences including temperature units (Fahrenheit or Celsius), light and dark mode, notification preferences by city and condition, and account information including display name and email.

---

## VI. Design (Wireframes)

**A. Design Philosophy**
When designing the application we wanted to make sure the rules we planned to set out were followed. We wanted the user to have quick and clear access without UI issues regarding information overload or clashing colors. The colors within the app were meant to work in tandem with the current conditions so the user has a seamlessly integrated UI and information experience.

**B. Home Screen Layout**
The home screen has the city name at the top followed by a weather icon reflecting current conditions, temperature, highs and lows, and relevant weather details. The bottom navigation bar houses the three tab options for the user to select at any time.

**C. My Cities Screen Layout**
This screen uses a search bar and Add button at the top where users can find and add cities. Each city card shows the city name, current conditions, and a small weather icon matching the home screen style.

**D. Wireframe Assets**
Full wireframe images and screenshots are available in the project GitHub repository:

https://github.com/JosephP312/SimpleWeatherApp

---

## README Reference

The full project README including setup instructions, architecture details, API configuration, and project structure is available here:

https://github.com/JosephP312/SimpleWeatherApp/blob/master/README.md
