import FirebaseLibraries.firebase_platform_bom

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    setCompileSdkVersion(Configs.compileSdkVersion)
    defaultConfig {
        setMinSdkVersion(Configs.minSdkVersion)
        setTargetSdkVersion(Configs.targetSdkVersion.toString())
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
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