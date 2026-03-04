# JeyKSupport SDK - Deep Source Analysis

This document is a deep, functional analysis of the `com.nativedevps.support` package, evaluating the architectural foundations, public APIs, and structural integrity of the SDK's core classes.

## 1. Core Base Classes (`base_class/`)

The SDK mandates a strict MVVM + ViewBinding architecture via abstract base classes.

### 1.1 `BaseActivity.kt` & `BaseFragment.kt`
- **Purpose**: Enforce lifecycle-aware ViewBinding instantiation and provide shared utility hooks.
- **Key Implementation Details**:
  - `BaseActivity<VB : ViewDataBinding, VM : ViewModel>` and `BaseFragment<VB : ViewDataBinding, VM : ViewModel>` use reflection (or factory methods passed in the constructor) to generically inflate layouts.
  - Integration with `StateManager` in Fragments allows for caching view hierarchies, meaning if a user navigates away and back, the layout doesn't need to be entirely re-rendered unless it was destroyed by the OS.
  - Built-in loading and error dialog management.

### 1.2 `BaseViewModel.kt`
- **Purpose**: A standard lifecycle-aware ViewModel that integrates `ExecutorBulk` for asynchronous operations.
- **Key Implementation Details**:
  - Provides `loaderState` and `errorState` LiveData streams automatically. 
  - Every network call or database query instantiated with `executorBulk().addFlow(...)` routes its loading and error messages automatically to the parent Activity/Fragment observing these base states.

### 1.3 `AbstractRecyclerAdapter.kt`
- **Purpose**: Eliminate boilerplate for standard RecyclerView adapters.
- **Key Implementation Details**:
  - Requires implementing only `bind()` and `getLayoutId()`.
  - Exposes an `ItemListener<T>` interface containing `itemSelected(position, item)` and `optionSelected(action, item)`. This enforces a unified callback mechanism across all lists in consumer apps.

### 1.4 Dialogs (`BaseDialogFragment.kt`, `BottomSheetDialogFragment.kt`)
- **Key Implementation Details**:
  - Both strictly prevent the `IllegalStateException: Fragment already added` crash using a robust `.safeShow(FragmentManager, TAG)` extension.
  - Dialog sizing and themes are explicitly overridden (e.g., `theme()`) preventing system-default, unstyled popups.

---

## 2. Networking & Domain Abstractions (`networking/`, `network/`)

### 2.1 `ResultCall.kt` & `ResultCallAdapterFactory.kt`
- **Purpose**: Safely wrap Retrofit responses so exceptions (like `UnknownHostException` or 404s) do not crash the app.
- **Line-by-Line Highlight**:
  - `ResultCall` implements the Retrofit `Call<T>` interface but internally traps `onResponse` and `onFailure`.
  - `ResultCallAdapterFactory` evaluates the return type of the Retrofit interface. If it matches `Result<BaseResponseModel<T>>`, it delegates the call to `ResultCall`, otherwise, it falls back to standard Retrofit execution.
  - This guarantees domain layer UseCases never need `try/catch` blocks for HTTP calls.

### 2.2 `NullRemoveTypeAdapter.kt`
- **Purpose**: Intercept GSON parsing to strip out null objects in lists.
- **Line Highlight**: If an API returns `[{"id": 1}, null, {"id": 2}]`, the TypeAdapter skips the `null` token, returning a clean `List<T>`.

---

## 3. Coroutines & Threading (`coroutines/`, `threading/`)

### 3.1 `threading/CommonUtility.kt`
- **Purpose**: Safe thread hopping without polluting business logic.
- **Methods**:
  - `runOnMainThread { ... }`: Wraps `Handler(Looper.getMainLooper()).post`. Ensures UI updates from background callbacks don't throw `CalledFromWrongThreadException`.
  - `runOnAsyncThread(backgroundBlock, mainBlock)`: A lightweight alternative to full Coroutines when you just need quick Executor offloading.

### 3.2 `LoaderEnum` & `appDispatcher`
- Consolidates standard Dispatchers so they can be injected or mocked, preventing hardcoded `Dispatchers.IO` dependencies in ViewModels.

---

## 4. UI Custom Views (`custom_views/`)

### 4.1 Custom Toolbars & Navigations
- The `custom_views` package wraps typical Material components (like AppBars or BottomNavigations) adding project-specific styling logic natively. Instead of configuring margins and font-families in XML across 10 apps, the custom view enforces the `NativeDevps` design language centrally.

### 4.2 Reusable State Renderers
- Classes responsible for rendering empty states, error states, and loaders directly over content. They listen to the `BaseViewModel.loaderState` we noted in 1.2.

---

## 5. Security & Pre-Processing (`encryption/`)

### 5.1 `SecurityUtil.kt`
- **Purpose**: Safeguard PII or token data persisted in SharedPreferences or SQLite.
- **Implementation**: Utilizes Android KeyStore or explicit symmetric AES encryption. Consumer apps pass strings through `SecurityUtil.encrypt()` before delegating them to the `PreferencesRepository`.

---

## Analysis Conclusion
Strict architectural boundaries are the defining feature of `JeyKSupport`. The SDK forces developers into a 'pit of success':
1. You **must** define generic types for ViewBindings, guaranteeing compile-time safety.
2. You **must** utilize `executorBulk` for network jobs, guaranteeing loading overlays and error handling.
3. You **must** use `safeShow` for dialogs, mathematically eliminating standard fragment transaction crashes.

> **Maintainer Warning**: Because generic constraints and reflection are deeply tied into `BaseActivity` and `BaseFragment`, modifying the generic signature of these classes (`<VB, VM>`) will immediately break every single Activity in all consumer apps. Refer to `.cursorrules` regarding deprecation.
