plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
    id("androidx.navigation.safeargs.kotlin")
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
    //SplashScreen
    implementation(libs.androidx.core.splashscreen)

    // Views/Fragments Navigation integration
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")


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
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")

    //Argon (Hash password)
    implementation("com.lambdapioneer.argon2kt:argon2kt:1.6.0")

    //Coil: Images
    implementation("io.coil-kt.coil3:coil:3.0.1")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.1")

    // Maps SDK for Android
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}