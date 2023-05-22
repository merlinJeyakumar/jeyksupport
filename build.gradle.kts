import FirebaseLibraries.firebase_platform_bom

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "nativedevps.support"
    compileSdk = Configs.compileSdkVersion
    defaultConfig {
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(RequiredLibraries.core_ktx)
    implementation(RequiredLibraries.core_ktx)
    implementation(RequiredLibraries.viewmodel_ktx)
    requiredLibraries()
    dateTimeLibraries()
    roomLibraries()
    supportLibraries()
    imageLoaderLibraries()
    firebaseLibraries()
    rxJavaLibraries()
    cryptoLibraries()
    networkLibraries()
    implementation(platform(firebase_platform_bom))
    firebaseLibraries()
}

kapt {
    correctErrorTypes = true
}