# JeykSupport

JeykSupport is a Kotlin Android support library that collects reusable foundations and utility code for Android applications using MVVM-style presentation layers, View/Data Binding, Kotlin Flow, and Retrofit-based networking.

The project is documented for source builds. A public dependency coordinate is intentionally not included until the repository's publishing metadata is reconciled.

## What it provides

### Application foundations

- `BaseActivity`, `BaseFragment`, and `BaseViewModel` abstractions for common Android presentation layers.
- `FlowUseCase` and `NetworkResult` types for exposing success, error, and status states through Kotlin Flow.
- Shared patterns for View/Data Binding-based activities and fragments.

### Platform and integration utilities

- Runtime-permission, keyboard, device, network, storage, file, MIME-type, and notification helpers.
- UI, adapter, RecyclerView, and custom-view helpers.
- Coroutine and main-thread/background-thread utilities.
- Retrofit `ResultCallAdapterFactory` and response-wrapper support.
- Room and shared-preference helpers.
- Logging plus encryption, hashing, and keystore-related helper code.

Use only the APIs that fit the host application's architecture and Android-version requirements.

## Repository layout

| Path | Purpose |
| --- | --- |
| `app/` | Sample and consumer Android application module. |
| `jeyksupport/` | Reusable Android library module. |
| `buildSrc/` | Shared Gradle build configuration and version constants. |
| `utilities.md` | Additional utility-package reference material. |

## Requirements

The current project configuration uses:

- JDK 17
- Kotlin Android plugin 1.8.20
- Android Gradle Plugin 7.4.2
- Android `minSdk 21`
- Android `compileSdk 33` and `targetSdk 33`

View Binding and Data Binding are enabled in the library module. The project also includes Hilt support for applications that use dependency injection.

## Build from source

Clone the repository and build the Android library module:

```bash
./gradlew :jeyksupport:assembleDebug
```

On Windows:

```bat
gradlew.bat :jeyksupport:assembleDebug
```

Open the repository in Android Studio with JDK 17 and an Android SDK compatible with the configured compile SDK.

## Result and use-case model

`FlowUseCase<P, T>` exposes a `Flow<NetworkResult<T>>`. The result hierarchy includes `SuccessApiResult<T>` for successful values, `ErrorApiResult<T>` for an error message and optional throwable, and `StatusApiResult<T>` for informational, error, or progress updates.

## Additional references

- [Utility-package documentation](utilities.md)
- [Library module notes](jeyksupport/README.md)

## Contribution guidance

This library is consumed by Android projects. Preserve public APIs, use deprecation paths when an API needs to evolve, and validate the library module before changing shared utility behaviour.