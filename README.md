# Fetch Exercise Android Application

This is an Android application built as part of an interview project. The app displays a list of expandable list IDs, with each list containing items sorted by their IDs. The project uses modern Android development practices and libraries such as Jetpack Compose, MVVM, Retrofit, Kotlin Coroutines, and Flow.

## Features
- **Jetpack Compose**: Used for building the UI in a declarative way.
- **MVVM Architecture**: Ensures separation of concerns and facilitates testability.
- **Material UI**: Implements Google's Material Design guidelines.
- **Retrofit**: Handles network requests.
- **Kotlin Coroutines and Flow**: Manages asynchronous tasks and data streams.
- **Expandable Lists**: Shows list of IDs that can be expanded to display sorted list items.
- **Version Catalog**: Dependencies are managed using a `libs.version.toml` file.
- **Unit Testing**: ViewModel is unit tested using MockK.

## Project Structure

```bash
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── res/
│   ├── libs.version.toml // handling all dependencies and versions
│   └── build.gradle
└──
```

## Dependencies

- **Jetpack Compose**
- **Material UI**
- **Retrofit**
- **Kotlin Coroutines**
- **Kotlin Flow**
- **MockK** (for unit tests)

All versions are managed using a version catalog defined in the `libs.version.toml` file.

## Unit Tests

- **ViewModel Unit Testing**: The `ViewModel` is tested using MockK to ensure proper functionality and coverage.

## How to Build

1. Clone the repository:
    ```bash
    git clone https://github.com/stTy102/Fetch-excersize.git
    ```
2. Open the project in Android Studio.
3. Sync Gradle to download dependencies.
4. Run the project on an emulator or a physical device.
