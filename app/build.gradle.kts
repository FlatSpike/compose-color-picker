@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.github.flatspike.compose.color.picker.example"

    defaultConfig {
        applicationId = "com.github.flatspike.color.picker.example"

        minSdk = 22
        targetSdk = 34
        compileSdk = 34

        versionCode = 13
        versionName = "0.0.1"

        multiDexEnabled = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        isCoreLibraryDesugaringEnabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    buildTypes {
        create("profile") {
            initWith(getByName("debug"))
            isDebuggable = false
            isProfileable = true
            matchingFallbacks += listOf("release", "debug")
        }
    }
}

dependencies {
    implementation(project(":color-picker"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.window)
    implementation(libs.google.android.material)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.core)

    coreLibraryDesugaring(libs.android.tools.desugar)
}
