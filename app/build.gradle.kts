

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs")
    kotlin("android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.nikitosii.studyrealtorapp"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.nikitosii.studyrealtorapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 5
        versionName = "1.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://realtor16.p.rapidapi.com/\"")
        buildConfigField("String", "Image_URL", "\"https://api.unsplash.com/\"")
    }

    signingConfigs {
        create("release") {
            keyAlias = "studyRealtorApp"
            keyPassword = "ifatur23"
            storeFile = file("../keys/release_keys.jks")
            storePassword = "ifatur23"
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isMinifyEnabled = false
            isDebuggable = true
            signingConfig = signingConfigs["release"]
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    val dagger = "2.50"
    val hawk = "2.0.1"
    val timber = "4.7.1"
    val kotlin = "1.6.20"
    val retrofit = "2.9.0"
    val okhttp = "4.7.0"
    val chunk = "3.5.0"
    val coroutine = "1.6.4"
    val coroutineAndroid = "1.6.4"
    val lifecycle = "2.6.2"
    val lifecycleExtension = "2.2.0"
    val moshi = "1.15.0"
    val moshiConverter = "2.6.2"
    val navVersion = "2.3.3"
    val room = "2.3.0"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx:21.6.1")
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.6.3")
    implementation("com.google.firebase:firebase-dynamic-links-ktx:21.2.0")

    //Dagger
    implementation("com.google.dagger:dagger:$dagger")
    kapt("com.google.dagger:dagger-compiler:$dagger")
    implementation("com.google.dagger:dagger-android:$dagger")
    implementation("com.google.dagger:dagger-android-support:$dagger")
    kapt("com.google.dagger:dagger-android-processor:$dagger")

    //Okhttp + Retrofit
    implementation("com.squareup.okhttp3:okhttp:$okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp")
    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")

    //Chunk
    debugImplementation("com.github.chuckerteam.chucker:library:$chunk")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:$chunk")

    //Hawk
    implementation("com.orhanobut:hawk:$hawk")

    //Timber
    implementation("com.jakewharton.timber:timber:$timber")

    //Moshi
    implementation("com.squareup.moshi:moshi-kotlin:$moshi")
    implementation("com.squareup.moshi:moshi-adapters:$moshi")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi")
    implementation("com.squareup.retrofit2:converter-moshi:$moshiConverter")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")


    //Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineAndroid")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin")

    //livedata
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleExtension")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")

    //Firebase crashlytics
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))

    // Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    //SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.0")

    //Navigation

    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.fragment:fragment:1.2.5")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Expandable Layout
    implementation("com.github.skydoves:expandablelayout:1.0.7")
    implementation("com.android.installreferrer:installreferrer:2.2")
    implementation("com.google.firebase:firebase-dynamic-links-ktx:20.1.1")

    // Bottom Nav Bar
    implementation("np.com.susanthapa:curved_bottom_navigation:0.6.5")

    // lottie
    implementation("com.airbnb.android:lottie:5.0.3")
    //Glide
    implementation("com.github.bumptech.glide:glide:4.14.2")
    kapt("com.github.bumptech.glide:compiler:4.14.2")
    //Room
    implementation("androidx.room:room-runtime:$room")
    kapt("androidx.room:room-compiler:$room")
    implementation("androidx.room:room-ktx:$room")
    //Picasso
    implementation("com.squareup.picasso:picasso:2.71828")
    //Swipe Refresh
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    // Expandable Layout
    implementation("com.github.skydoves:expandablelayout:1.0.7")

    //Chunk
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2") {
        exclude("com.google.android.material", "material")
    }
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2") {
        exclude("com.google.android.material", "material")
    }

    //Gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("com.viceboy.library:triangularshadeimageview:1.0.1")
    implementation("it.sephiroth.android.library.rangeseekbar:rangeseekbar:1.1.0")
}