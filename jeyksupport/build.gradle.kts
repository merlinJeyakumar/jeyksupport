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

    lint {
        baseline = file("lint-baseline.xml")
    }

    packagingOptions {
        exclude("META-INF/LICENSE")
        exclude("META-INF/NOTICE")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/main.kotlin_module")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/library_release.kotlin_module")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        resources.excludes.add("META-INF/*")
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
            afterEvaluate {
                artifact(tasks.getByName("bundleReleaseAar"))
            }
        }
    }
}
tasks.register<Copy>("copyDependencies") {
    from(configurations.kotlinCompilerClasspath)
    into("$buildDir/libs")
}

tasks.register<Zip>("createAAR") {
    from(android.sourceSets["main"].java.srcDirs)
    from(android.sourceSets["main"].res.srcDirs)
    from(android.sourceSets["main"].assets.srcDirs)
    from(android.sourceSets["main"].jniLibs.srcDirs)
    from(android.sourceSets["main"].manifest.srcFile)
    from(tasks.named("copyDependencies"))

    archiveBaseName.set("your-library-name")
    archiveVersion.set("1.0.0")
    destinationDirectory.set(file("$buildDir/outputs/aar"))
    archiveExtension.set("aar")
}

tasks.named("assemble") {
    dependsOn("createAAR")
}