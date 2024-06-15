plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.ents_h108.petwell"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.ents_h108.petwell"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://petwell-api-wmaq4jxv4a-et.a.run.app/\"")
        buildConfigField("String", "GEMINI_API_KEY", "\"YOUR_API_KEY\"")
        buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"346975805406-lemndt4g4qs63ri355v4k1pkl4fqfmn4.apps.googleusercontent.com\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Splash Screen
    implementation(libs.androidx.core.splashscreen)

    // Navigation
    implementation(libs.androidx.navigation.fragment)

    // Image Loader
    implementation(libs.circleimageview)
    implementation(libs.coil)
    implementation(libs.shimmer)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.preferences)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.firebase.storage)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // View Pager
    implementation(libs.androidx.viewpager2)

    // Google Auth
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // FAB
    implementation (libs.androidx.legacy.support.v4)

    // Gemini
    implementation(libs.generativeai)

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx)

    // Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}