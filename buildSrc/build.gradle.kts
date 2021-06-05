plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    jcenter()   // JCenter is at end of life
}

object VersionIn {
    const val androidGradlePlugin = "7.1.0-alpha01"
    const val kotlin = "1.5.10"
    const val dokka = "1.4.32"
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${VersionIn.kotlin}")
    implementation("com.android.tools.build:gradle:${VersionIn.androidGradlePlugin}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${VersionIn.dokka}")
    implementation("org.jetbrains.dokka:dokka-core:${VersionIn.dokka}")
}