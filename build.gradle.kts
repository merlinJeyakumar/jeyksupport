import FirebaseLibraries.firebase_platform_bom

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
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
        buildConfig = true
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
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
    navigationLibrary()
}

kapt {
    correctErrorTypes = true
}

publishing {
    publications {
        create<MavenPublication>("ReleaseAar") {
            groupId = "com.github.merlinJeyakumar"
            artifactId = "library"
            version = "1.1"
            afterEvaluate { artifact(tasks.getByName("bundleReleaseAar"))
            }
    }
}
}