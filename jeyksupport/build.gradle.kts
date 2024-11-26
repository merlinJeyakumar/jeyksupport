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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar", "*.jar"))))
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