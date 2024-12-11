<h1 align="center">Crypto Market</h1>

<br />
<p align="center">
  <img alt="API" src="https://img.shields.io/badge/Api%2021+-50f270?logo=android&logoColor=black&style=for-the-badge"/>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-a503fc?logo=kotlin&logoColor=white&style=for-the-badge"/>
  <img alt="Jetpack Compose" src="https://img.shields.io/static/v1?style=for-the-badge&message=Jetpack+Compose&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label="/> 
  <img alt="material" src="https://custom-icon-badges.demolab.com/badge/material%20you-lightblue?style=for-the-badge&logoColor=333&logo=material-you"/>
  <br />
  <br />
  <a href="https://github.com/agung-prabowo/Crypto-Market/stargazers"><img alt="stars" src="https://img.shields.io/github/stars/agung-prabowo/crypto-market?color=ffff00&style=for-the-badge"/></a>
  <a href="https://hits.sh/github.com/agung-prabowo/crypto-market/"><img alt="Hits" src="https://hits.sh/github.com/agung-prabowo/Crypto-Market.svg?style=for-the-badge&label=Views&extraCount=10&color=54856b"/></a>
  <br />
</p>

# Features ✨

_Crypto Market_ focuses on the following key things:

- Clean and Simple Material UI. 🎨
- Dark mode. 🌗
- List Coin and Exchange 📙
- Chart Coin 📈
- Jetpack Compose UI. 🖌

# Built with 🛠

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous calls and tasks to utilize threads.
- [Jetpack Compose UI Toolkit](https://developer.android.com/jetpack/compose) - Modern UI development toolkit.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#:~:text=StateFlow%20is%20a%20state%2Dholder,property%20of%20the%20MutableStateFlow%20class.) - StateFlow and SharedFlow are Flow APIs that enable flows to optimally emit state updates and emit values to multiple consumers.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
  - [Hilt-Dagger](https://dagger.dev/hilt/) - A standard way to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting ```ViewModel```.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Accompanist](https://google.github.io/accompanist/) - A collection of extension libraries for Jetpack Compose.
- [Serialization](https://github.com/Kotlin/kotlinx.serialization) - A Java serialization/deserialization library to convert Java Objects into JSON and back
- [Coil](https://github.com/coil-kt/coil) - Image loading for Android backed by Kotlin Coroutines.
- [Ktor](https://github.com/ktorio/ktor) - Ktor is an asynchronous framework for creating microservices, web applications and more. Written in Kotlin from the ground up. ```WebSocket```

# Architecture 👷‍♂️
This app uses [MVVM(Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch) architecture.

![MVVM](assets/mvvm.png)
