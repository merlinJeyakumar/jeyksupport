import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.requiredLibraries() {
    api(RequiredLibraries.kotlinStdLib)
    api(RequiredLibraries.core_ktx)
    api(RequiredLibraries.coroutines_android)
    //api(RequiredLibraries.anko)
    //api(RequiredLibraries.anko_commons)
    api(RequiredLibraries.coroutines_core)
    api(RequiredLibraries.coroutines_test)
    api(RequiredLibraries.lifecycle_extension)
    api(RequiredLibraries.viewbinding)
    api(RequiredLibraries.multi_dex)
    api(RequiredLibraries.gson)
    api(RequiredLibraries.timber)
    api(RequiredLibraries.runtime_ktx)
    api(RequiredLibraries.hilt_android)
    //api(RequiredLibraries.hilt_lifecycle_viewmodel)
    kapt(RequiredLibraries.kapt_hilt_android_compiler)
    kapt(RequiredLibraries.kapt_hilt_compiler)
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.3.0")
    api(RequiredLibraries.databinding_runtime)
    api(RequiredLibraries.viewmodel_ktx)
    api(RequiredLibraries.livedata_ktx)
}

fun DependencyHandler.supportLibraries() {
    implementation(SupportLibraries.appCompat)
    implementation(SupportLibraries.support_design)
    implementation(SupportLibraries.constraintLayout)
    implementation(SupportLibraries.sdp_android)
    implementation(SupportLibraries.ssp_android)
    implementation(SupportLibraries.material)
    implementation(SupportLibraries.recyclerview)
    implementation(SupportLibraries.card_view)
    implementation(SupportLibraries.legacy_support)
    implementation(SupportLibraries.paging_runtime)
    implementation(SupportLibraries.viewpager2)
    implementation(SupportLibraries.activity_ktx)
    implementation(SupportLibraries.fragment_ktx)
    implementation(SupportLibraries.lottie)
    implementation(SupportLibraries.shimmer)
    implementation(SupportLibraries.libphonenumber)
    implementation(SupportLibraries.work_runtime_ktx)
    implementation(SupportLibraries.work_hilt)
}

fun DependencyHandler.imageLoaderLibraries() {
    implementation(ImageLoaderLibraries.glide)
    implementation(ImageLoaderLibraries.glide_integration)
    kapt(ImageLoaderLibraries.kapt_glide_integration)
}

fun DependencyHandler.rxJavaLibraries() {
    implementation(RxJavaLibraries.rx_java)
    implementation(RxJavaLibraries.rx_kotlin)
    implementation(RxJavaLibraries.rx_android)
    implementation(SupportLibraries.paging_rxjava)
    implementation(SupportLibraries.work_rxjava3)
}

fun DependencyHandler.networkLibraries() {
    implementation(NetworkLibraries.retrofit)
    implementation(NetworkLibraries.rx_adapter)
    implementation(NetworkLibraries.retrofit_converter_gson)
    implementation(NetworkLibraries.okhttp)
    implementation(NetworkLibraries.okhttp_logging_interceptor)
    //api(NetworkLibraries.stetho)
    //api(NetworkLibraries.stetho_okhttp3)
    debugImplementation(NetworkLibraries.chucker_debug)
    releaseImplementation(NetworkLibraries.chucker_release)
}

fun DependencyHandler.roomLibraries() {
    implementation(RoomLibraries.room_runtime)
    implementation(RoomLibraries.room_ktx)
    implementation(RoomLibraries.room_paging)
    kapt(RoomLibraries.kapt_room_compiler)
}

fun DependencyHandler.dateTimeLibraries() {
    implementation(DateTimeLibraries.joda_time)
    implementation(DateTimeLibraries.joda_convert)
}

fun DependencyHandler.exoPlayerLibraries() {
    implementation(ExoPlayerLibraries.exoplayer_core)
    implementation(ExoPlayerLibraries.exoplayer_dash)
    implementation(ExoPlayerLibraries.exoplayer_ui)
    implementation(ExoPlayerLibraries.magical_exo_player)
}

fun DependencyHandler.dataStoreLibraries() {
    //api(DataStoreLibraries.protobuf)
    implementation(DataStoreLibraries.data_store)
}

fun DependencyHandler.dataStoreLite() {
    implementation(DataStoreLibraries.data_store_preferences)
}

fun DependencyHandler.testLibraries() {
    implementation(TestLibraries.espressoCore)
    implementation(TestLibraries.junit)
    implementation(TestLibraries.junitTest)

    androidTestImplementation(TestLibraries.androidTestImplementationRobolectric)
    androidTestAnnotationProcessor(TestLibraries.androidTestAnnotationProcessor)
    kaptAndroidTest(TestLibraries.kaptAndroidTest)
    testAnnotationProcessor(TestLibraries.testAnnotationProcessorHiltAndroidTesting)
    androidTestImplementation(TestLibraries.androidTestImplementationHiltAndroidTesting)
    testImplementation(TestLibraries.testImplementationHiltAndroidTesting)
}

fun DependencyHandler.firebaseLibraries() {
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-dynamic-links")
    implementation(FirebaseLibraries.firebase_referral)
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
}

fun DependencyHandler.firebaseExternalLibraries() {
    implementation(FirebaseLibraries.firebase_database)
}

fun DependencyHandler.thirdPartyLibraries() {
    implementation(ThirdPartyLibraries.otpview_pinview)
    implementation(ThirdPartyLibraries.material_spinner)
    implementation("com.github.merlinJeyakumar:notify:1.0.6")
    implementation("com.wdullaer:materialdatetimepicker:4.2.3") //https://github.com/wdullaer/MaterialDateTimePicker
}

fun DependencyHandler.cryptoLibraries() {
    implementation("com.google.crypto.tink:tink-android:1.6.1")
}

fun DependencyHandler.navigationLibrary(){
    implementation(NavigationLibraries.navigationUi)
    implementation(NavigationLibraries.navigationFragment)
}