# App with using MVVM + Data Binding + Retrofit + DI
Languages, libraries and tools used
* [Kotlin](https://kotlinlang.org)
* [Android Architecture Components Navigation](https://github.com/googlesamples/android-architecture-components/tree/master/NavigationBasicSample)
* [Room](https://square.github.io/retrofit/) Persistence Library
* [GSON](https://github.com/google/gson)
* [Kotlin Android Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
* [AndroidX Lifecycle-aware Components](https://github.com/googlecodelabs/android-lifecycles)
* Network communication with [Retrofit 2](https://square.github.io/retrofit/)
* Dependency Injection with [KodeIn](https://github.com/Kodein-Framework/Kodein-DI)
* [ThreeTen Android Backport](https://github.com/JakeWharton/ThreeTenABP)
* [Glide](https://bumptech.github.io/glide/)
* [AndroidX Preference Library](https://github.com/googlesamples/android-preferences)
* [New Material Design Library](https://material.io/develop/android/docs/getting-started/)
* [Facebook shimmer effect dependency](https://github.com/facebook/shimmer-android)
* [CircleImageView](https://github.com/lopspower/CircularImageView)
* [Material Dialogs](https://github.com/afollestad/material-dialogs)
* [VK SDK](https://github.com/VKCOM/vk-android-sdk)

## Requirements
* JDK 1.8
* [Android Studio 3.6 Preview](https://developer.android.com/studio/preview/)
* Android 9 ([API level 28](https://developer.android.com/studio/releases/platforms#9.0))
* Latest Android SDK Tools and build tools.

## Architecture
This project follows Android architecture guidelines that are based on MVVM.

## Project structure
- data
  - db
    - converters
    - entity
    - unitlocalized
  - network
    - api
    - data
    - response
  - provider
  - repository
- internal
  - bindings
  - dto
  - enums
  - glide
  - observer
- ui
  - base
  - extensions
    - behavior
  - main
  - postponed
    - detali
    - list
  - settings
  - suggest
    - detali
    - list
  - timeline
    - detali
    - list

## Overview
This project aims to demonstrate the usage of Android Architecture Components and how this components can be used in an application with a MVVM architecture in Kotlin.

Timeline                   |  Postponed                |  Settings
:-------------------------:|:-------------------------:|:-------------------------:
![alt-timeline](https://github.com/Holofox/android-anicoubs/blob/master/screenshots/timeline.png "Timeline")  |  ![alt-postponed](https://github.com/Holofox/android-anicoubs/blob/master/screenshots/postponed.png "Postponed") | ![alt-settings](https://github.com/Holofox/android-anicoubs/blob/master/screenshots/settings.png "Settings")
Add Coub                   |  Select Category          |  Select Date Time
![alt-dialog_add_coub](https://github.com/Holofox/android-anicoubs/blob/master/screenshots/dialog_add_coub.png "Add coub")  |  ![alt-dialog_select_item](https://github.com/Holofox/android-anicoubs/blob/master/screenshots/dialog_select_item.png "Select Category") | ![alt-dialog-select_datetime](https://github.com/Holofox/android-anicoubs/blob/master/screenshots/dialog_select_datetime.png "Select Date Time")

## References
* [Android Documentation](https://developer.android.com/topic/libraries/architecture)
* [Android Kotlin Fundamentals](https://codelabs.developers.google.com/codelabs/kotlin-android-training-welcome/#0)

## License

This source is released under the [MIT License](https://github.com/Holofox/android-anicoubs/blob/master/LICENSE).
