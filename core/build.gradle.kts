version = "1.0.1"
description = "Kotlin extensions for \"Brother Print SDK for Android\" " +
    "(supported models MPring, PocketJet, and RJ/TD/QL/PT series)"

plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    publish
}

android {
    compileSdkVersion(Sdk.compile)

    defaultConfig {
        minSdkVersion(Sdk.min)
        targetSdkVersion(Sdk.target)

        versionCode = 1
        versionName = version.toString()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(Version.java)
        targetCompatibility = JavaVersion.toVersion(Version.java)
    }
    kotlinOptions {
        jvmTarget = Version.java
        freeCompilerArgs += listOf("-Xexplicit-api=strict", "-Xopt-in=kotlin.RequiresOptIn")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    lint {
        isWarningsAsErrors = true
        isAbortOnError = true
    }
}

dependencies {
    compileOnly(moduleBrotherPrintLibrary)
    implementation(Lib.androidxPreferences)
    implementation(Lib.kotlinxCoroutines)
}
