plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.syntaxmate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.syntaxmate"
        minSdk = 26
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {
    implementation(libs.androidx.navigation.runtime.ktx)

    // ======== Versões ========
    val compose_version = "1.9.3"
    val compiler_version = "1.5.15"
    val material_version = "1.7.8"
    val navigation_version = "2.7.3"
    val room_version = "2.6.1"
    val gson_version = "2.12.1"

    // ======== Dependências Principais========
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))

    // ======== Compose/UI========
    implementation("androidx.compose.compiler:compiler:$compiler_version") // @composable
    implementation("androidx.activity:activity-compose:$compose_version") // setContent
    implementation("androidx.compose.material:material:$material_version") // Text
    implementation("androidx.navigation:navigation-compose:$navigation_version")

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // ======== Testes ========
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // ======== Debug ========
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ======== Android Room ========
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-ktx:$room_version")

    //coroutine
    implementation("androidx.room:room-ktx:$room_version")

    //gson
    implementation("com.google.code.gson:gson:$gson_version")
}