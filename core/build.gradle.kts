version = "1.0.0"
description = "This is core"

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
    implementation(Lib.androidxAppcompat)
    implementation(Lib.androidxCoreKtx)

    testImplementation(Lib.testJunit)

    LibGroup.testAndroid.forEach { androidTestImplementation(it) }
}
