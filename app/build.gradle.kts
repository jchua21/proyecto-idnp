plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.androidx.navigation.safe.args)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.jean.touraqp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jean.touraqp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

secrets {

    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"

    ignoreList.add("sdk.*")
}

dependencies {
    // Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    //SplashScreen
    implementation(libs.androidx.core.splashscreen)

    // Views/Fragments Navigation integration
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.hilt.navigation.fragment)


    //Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.play.services.maps)
    ksp(libs.hilt.android.compiler)

    //Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    runtimeOnly(libs.androidx.fragment.ktx)

    //Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    // To use Kotlin symbol processing (ksp)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Firestore
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.firestore)

    //Argon (Hash password)
    implementation(libs.argon2kt)

    //Coil: Images
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

    // Maps SDK for Android
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}