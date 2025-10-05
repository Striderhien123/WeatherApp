// Add these standard imports for file handling at the very top
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

// 1. Load the local.properties file ONCE at the top
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")

if (localPropertiesFile.exists()) {
    // We can use the imported FileInputStream and Properties here
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "com.weatherapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.weatherapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // =======================================================
        // THE FIX: Correctly Load and Interpolate the API Key
        // =======================================================

        // 2. Access the API key from the localProperties variable loaded above
        // Use your correct API key length: c6ead67f87dcdd9a35fac20969e8e336
        val apiKey = localProperties.getProperty("OWM_API_KEY") ?: "NO_KEY"

        // 3. This syntax correctly generates the Java string literal for BuildConfig
        buildConfigField("String", "OWM_API_KEY", "\"$apiKey\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Networking Dependencies
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")

    // Image Loading
    implementation("com.github.bumptech.glide:glide:4.16.0")
}