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
- Retrofit result-call adapter support through the canonical `com.nativedevps.support.utility.network` package.
- Room and shared-preference helper bases that consumers should align with their own persistence schema.
- Logging and platform-signature helpers, plus cryptographic and key-management utilities.

Consumers should select and review the appropriate API for their Android-version and threat-model requirements.

## Repository layout

| Path | Purpose |
| --- | --- |
| `app/` | Consumer/scaffold module with dependency wiring and model/use-case examples; it is not an end-to-end demo application. |
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

View Binding and Data Binding are enabled in the library module. The project includes Hilt dependencies and application-scaffold wiring, but it does not provide a complete library-level dependency-injection solution.

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
