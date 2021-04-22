plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    jcenter()   // JCenter is at end of life
}

object VersionIn {
    const val androidGradlePlugin = "7.0.0-alpha14"
    const val kotlin = "1.4.32"
    const val dokka = "1.4.20"
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${VersionIn.kotlin}")
    implementation("com.android.tools.build:gradle:${VersionIn.androidGradlePlugin}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${VersionIn.dokka}")
    implementation("org.jetbrains.dokka:dokka-core:${VersionIn.dokka}")
}