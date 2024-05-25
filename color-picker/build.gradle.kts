@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.androidx.baselineprofile)
}

android {
    namespace = "com.github.flatspike.compose.color.picker"

    defaultConfig {
        minSdk = 22
        compileSdk = 34
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
}

baselineProfile {
    filter {
        include("com.github.flatspike.compose.color.picker.**")
    }
}

dependencies {
    configurations["baselineProfile"].apply {
        this(project(":baselineprofile"))
    }

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    coreLibraryDesugaring(libs.android.tools.desugar)
}
