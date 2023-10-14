package libraries

object Versions {

    // Required Core Libraries
    const val appCompatVersion = "1.6.1" //todo: https://androidx.tech/artifacts/appcompat/appcompat/
    const val core_ktx = "1.9.0" //todo: https://androidx.tech/artifacts/core/core-ktx/
    const val multi_dex = "2.0.1"
    const val lifecycle_extension = "2.2.0" //https://developer.android.com/jetpack/androidx/releases/lifecycle
    const val coroutines_android = "1.7.1" //https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-android
    const val coroutines_core = "1.7.1" //https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    const val coroutines_test = "1.7.1" //https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-test
    const val coroutines_play_services = "1.7.1" //https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-play-services
    const val json_serializer = "1.0.1"
    const val anko = "0.10.4" //https://mvnrepository.com/artifact/org.jetbrains.anko/anko?repo=springio-plugins-release
    const val anko_commons = "0.10.4" //https://mvnrepository.com/artifact/org.jetbrains.anko/anko-common?repo=springio-plugins-release
    const val activity_ktx = "1.7.1"
    const val fragment_ktx = "1.5.7"
    const val navigation_fragment_ktx = "2.5.2"
    const val databinding = "8.1.0-beta05" //https://mvnrepository.com/artifact/com.android.databinding/viewbinding?repo=google
    const val runtime_ktx = "2.6.1" //https://developer.android.com/jetpack/androidx/releases/lifecycle
    const val viewmodel_ktx = "2.6.1" //https://developer.android.com/jetpack/androidx/releases/viewmodel
    const val livedata_ktx = "2.6.1" //https://developer.android.com/jetpack/androidx/releases/lifecycle
    const val classpathDaggerHiltVersion = "2.46.1" //https://dagger.dev/hilt/gradle-setup.html
    const val hilt_android = classpathDaggerHiltVersion //"2.42" //https://dagger.dev/hilt/gradle-setup.html
    const val hilt_android_compiler = classpathDaggerHiltVersion//"2.42" //https://dagger.dev/hilt/gradle-setup.html
    const val hilt_compiler = "1.0.0" //https://developer.android.com/training/dependency-injection/hilt-jetpack
    const val hilt_lifecycle_viewmodel = "1.0.0-alpha03" //https://androidx.tech/artifacts/hilt/hilt-lifecycle-viewmodel/
    const val gson = "2.10.1" //https://mvnrepository.com/artifact/com.google.code.gson/gson
    const val timber = "5.0.1" //https://mvnrepository.com/artifact/com.jakewharton.timber/timber

    // Support Libraries
    const val constraintLayoutVersion = "2.1.4" //https://developer.android.com/jetpack/androidx/releases/constraintlayout
    const val materialVersion = "1.9.0" //https://github.com/material-components/material-components-android/releases
    const val support_design = "28.0.0" //https://developer.android.com/topic/libraries/support-library/packages
    const val sdp_android = "1.1.0" //https://github.com/intuit/sdp
    const val ssp_android = "1.1.0" //https://github.com/intuit/ssp
    const val recyclerView = "1.3.0" //https://androidx.tech/artifacts/recyclerview/recyclerview/
    const val card_view = "1.0.0"
    const val legacy_support = "1.0.0"
    const val paging_runtime = "3.1.0" //https://androidx.tech/artifacts/paging/paging-runtime/
    const val paging_rxjava3 = "3.0.1"
    const val viewpager2 = "1.1.0-beta01" //https://androidx.tech/artifacts/viewpager2/viewpager2/
    const val splash_screen = "1.0.0-rc01"
    const val lottie = "5.2.0"
    const val shimmer = "0.4.0"
    const val libPhoneNumber = "8.3.1"
    const val work_runtime_ktx = "2.8.1"
    const val work_hilt = "1.0.0"
    const val work_rxjava3 = "2.4.0"

    // RxJava
    const val rx_java = "3.1.6" //https://github.com/ReactiveX/RxJava
    const val rx_android = "3.0.2" //https://github.com/ReactiveX/RxAndroid
    const val rx_kotlin = "3.0.1" //https://github.com/ReactiveX/RxKotlin
    const val rx_data_store = "1.0.0"

    //Network Libraries
    const val retrofit = "2.9.0" //https://github.com/square/retrofit
    const val rx_adapter = "2.9.0"
    const val retrofit_converter_gson = "2.9.0"
    const val okhttp = "3.5.0.0-alpha.10.1"
    const val okhttp_logging_interceptor = "5.0.0-alpha.11"
    const val stetho = "1.5.1"
    const val stetho_okhttp3 = "1.5.1"
    const val chucker = "3.5.2"

    //Firebase Libraries
    const val classpathGoogleServices = "4.3.10"
    const val classpathCrashlytics = "2.7.1"
    const val classpathFirebasePerfs = "1.4.0"
    const val firebase_bom = "31.1.1"
    const val firebase_referral = "2.2"
    const val playservices_coroutines = "1.6.4"

    //Database Libraries
    const val room_runtime = "2.6.0-alpha01"
    const val room_ktx = room_runtime
    const val room_rxjava3 = room_runtime
    const val room_paging = room_runtime

    //DateTime Libraries
    const val joda_time = "2.10"
    const val joda_convert = "2.1.1"

    //ExoPlayer Libraries
    const val exoplayer_core = "2.15.1"
    const val exoplayer_dash = "2.15.1"
    const val exoplayer_ui = "2.15.1"
    const val magical_exo_player = "1.0.15"

    //ImageLoader Libraries
    const val glide = "4.11.0"
    const val glide_transformations = "4.1.0"
    const val glide_integration = "4.7.1"

    //ThirdParty Libraries
    const val jsoup = "1.11.3"

    // Test
    const val junitVersion = "4.13.2"
    const val junitTestVersion = "1.1.5"
    const val espressoCoreVersion = "3.4.0"
    const val hiltAndroidTesting = "2.38.1"
    const val hiltAndroidCompiler = "2.41"

    // DataStore
    const val protobuf_plugin = "0.8.19" //https://github.com/google/protobuf-gradle-plugin
    const val protobuf_protoc = "3.8.0" //https://github.com/google/protobuf-gradle-plugin
    const val protoc_gen_javalite = "3.0.0" //https://github.com/google/protobuf-gradle-plugin
    const val protoc_grpc = "1.0.0-pre2" //https://github.com/google/protobuf-gradle-plugin
    const val data_store = "1.1.0-dev01" //https://developer.android.com/jetpack/androidx/releases/datastore
}
