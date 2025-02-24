# Thread App

## Overview
Thread App is a social media application built using Jetpack Compose and Firebase. It allows users to create threads, comment, and interact with posts in real time.

## Features
- User Authentication (Firebase Authentication)
- Create, Read, Update, Delete (CRUD) Threads
- Real-time Database (Firestore)
- Image Upload (Firebase Storage)
- Like and Comment System
- Modern UI with Jetpack Compose
- Offline Data Caching

## Tech Stack
- **Kotlin**: Primary language
- **Jetpack Compose**: UI toolkit
- **Firebase Authentication**: User authentication
- **Firebase Firestore**: Real-time database
- **Firebase Storage**: Image uploads
- **Hilt**: Dependency injection
- **Coroutines & Flow**: Asynchronous programming
- **Navigation Component**: Screen navigation
- **Coil**: Image loading

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/Abhi95081/Threads.git
   cd thread-app
   ```
2. Open the project in Android Studio.
3. Add Firebase to the project:
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project
   - Add an Android app with your package name
   - Download and place `google-services.json` in `app/` directory
4. Enable Firebase services:
   - Authentication (Email/Password, Google Sign-In)
   - Firestore Database (Set rules)
   - Firebase Storage (Set rules)
5. Sync the project and build.
6. Run the app on an emulator or a real device.

## Folder Structure
```
📂 thread-app
 ┣ 📂 app
 ┃ ┣ 📂 src/main/java/com/example/threadapp
 ┃ ┃ ┣ 📂 data  # Firebase-related operations
 ┃ ┃ ┣ 📂 ui  # Compose UI screens
 ┃ ┃ ┣ 📂 viewmodel  # ViewModels
 ┃ ┃ ┗ MainActivity.kt
 ┃ ┣ 📜 AndroidManifest.xml
 ┗ 📜 build.gradle (app)
```

## Future Improvements
- Dark Mode support
- Push Notifications using Firebase Cloud Messaging (FCM)
- Profile customization
- Follow system

## License
This project is licensed under the MIT License.

---
Feel free to contribute or report issues!

