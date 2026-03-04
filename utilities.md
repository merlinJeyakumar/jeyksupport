# JeyKSupport Utilities Documentation

The `com.nativedevps.support.utility` package is the core backbone of the JeyKSupport SDK. It contains 50+ Kotlin utility files categorized into 26 specific domains, providing a comprehensive suite of tools for Android development, saving time, reducing boilerplate, and enforcing safe, standard practices across NativeDevps apps.

## Primary Utility Categories

### 1. Device & Hardware (`device/`)
A vast collection of helpers dealing with the physical device and OS environment.
- **Audio/Media**: `DeviceAudio.kt` for audio stream management.
- **Keyboard**: `keyboard/Common.kt` for toggling the soft keyboard safely.
- **Permissions**: `permission/EasyPermissions.kt` and `Utility.kt` for streamlined Android runtime permission requests.
- **Storage & Files**: 
  - `storage_access_framework/UriHelper.kt` & `Utility.kt` abstractions for Scoped Storage and SAF.
  - `file/Common.kt` for File I/O operations.
  - `mime/MimeWildCard.kt` for MIME type resolutions.
- **Networking**: `network/Utils.kt` and `network/download.kt` for checking internet availability and downloading files to device storage.
- **Notifications**: `notification/Common.kt` for generating local notifications and channels.

### 2. Date & Time (`date_time_utility/`)
Standardized date, time, and millisecond parsing/formatting.
- `DateTimeConversion.kt` & `DateUtility.kt`: Convert strings to dates, handle ISO parsing.
- `TimeUtility.kt` & `MillisecondUtility.kt`: Handle durations, stopwatches, and epoch timestamp math.

### 3. UI, Views & Fragments (`view/`, `fragment/`)
Boilerplate reduction for the Android UI and lifecycle.
- **Fragments**: `fragment/Utility.kt`, `fragment_extension.kt`, and `StateManager.kt` simplify Fragment transactions, backstack management, and state restoration.
- **Views**: Multiple `view/` utilities map common UI manipulations (`visible()`, `gone()`, `enable()`, `disable()`, and keyboard insets) into rapid extension functions.

### 4. Networking & API (`networking/`, `network/`, `retrofit/`)
Networking abstractions and HTTP tooling.
- **Retrofit & Calls**: `ResultCall.kt` and `ResultCallAdapterFactory.kt` wrap Retrofit calls into safe `Result` wrappers, automatically handling HTTP errors and parsing exceptions gracefully.
- **Data Adapters**: `NullRemoveTypeAdapter.kt` strips nulls from JSON responses during serialization.
- **Emulators**: `emulateNetworkCall.kt` provides mock delay streams for testing and UI state rendering.

### 5. Security & Encryption (`encryption/`)
Data protection and cryptography.
- `SecurityUtil.kt` & `Utility.kt`: Provides baseline hashing, encryption (AES), and possibly keystore access, securing local data before inserting it into Preferences or Room.

### 6. Event Handling (`event/`)
- `EventBus.kt`: A lightweight publisher/subscriber instance.
- `OnSingleClickListener.kt`: A debounced click listener ensuring users cannot spam-click buttons, preventing double-navigation or double-submissions.

### 7. Core Kotlin & Logic (`collections/`, `calculation/`, `text/`, `validation/`)
Standard library extensions.
- **Text & Strings**: `TextCasing.kt`, `FormattingUtils.kt`, and `text/CommonUtility.kt` for capitalization, regex, and span builders.
- **Collections**: `collections/Utility.kt` maps, filters, and transforms standard lists.
- **Validation**: `ValidationUtility.kt` for email, password strength, and input sanitization.

### 8. Database & Preferences (`room/`, `shared_preference/`)
- `DateConverter.kt`: Room TypeConverters for seamless timestamp storage.
- `shared_preference`: Extensions for standard `SharedPreferences` (or DataStore setups).

### 9. Asynchronous Execution (`threading/`)
- `threading/CommonUtility.kt`: Simplifies thread hopping (`runOnMainThread`, `runOnAsyncThread`) and coroutine dispatcher management without polluting business logic.

### 10. Debugging (`debugging/`)
- `Log.kt`, `LogJ.kt`: Custom, tag-formatted logging classes that strip out logs in production builds automatically while preserving them in debug environments.

## How to Use
Because these utilities are built predominantly using **Kotlin Extension Functions**, you rarely need to instantiate generic utility classes. 

Simply import the required function statically and use it directly on the object:
```kotlin
// Example 1: View Visibility
myButton.gone() 

// Example 2: Debounced Click
myButton.singleClickListener { submitForm() }

// Example 3: Networking Call Result
val response = api.fetchData()
response.onSuccess { data -> ... }.onFailure { error -> ... }
```
