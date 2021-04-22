fun String.isStableVersion(): Boolean {
    val upperCase = toUpperCase(java.util.Locale.ROOT)
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { it in upperCase }
    return stableKeyword || Regex("^[0-9,.v-]+(-r)?$").matches(this)
}

fun String.isNotStableVersion() = !isStableVersion()

object Sdk {
    const val min = 21
    const val target = 30
    const val compile = 30
}

object Version {
    const val java = "11"
    const val kotlin = "1.4.32"

    // Libs

    const val androidxAppcompat = "1.2.0"
    const val androidxCoreKtx = "1.3.2"
    const val androidxTestExt = "1.1.2"
    const val androidxTest = "1.3.0"
    const val compose = "1.0.0-beta04"
    const val composeAccompanistCoil = "0.7.0"
    const val composeActivity = "1.3.0-alpha06"
    const val composeLifecycleViewmodel = "1.0.0-alpha04"
    const val dokka = "1.4.20"
    const val ktlint = "0.40.0"

    // Test libs

    const val testEspresso = "3.3.0"
    const val testJunit = "4.13.2"

    // Gradle plugins

    const val pluginDetekt = "1.16.0"
    const val pluginKtlint = "10.0.0"
    const val pluginBenManesVersions = "0.38.0"
}

object Lib {
    const val androidxAppcompat = "androidx.appcompat:appcompat:${Version.androidxAppcompat}"
    const val androidxCoreKtx = "androidx.core:core-ktx:${Version.androidxCoreKtx}"

    // Compose
    const val composeUi = "androidx.compose.ui:ui:${Version.compose}"
    const val composeFoundation = "androidx.compose.foundation:foundation:${Version.compose}"
    const val composeMaterial = "androidx.compose.material:material:${Version.compose}"
    const val composeIconsCore = "androidx.compose.material:material-icons-core:${Version.compose}"
    const val composeIconsExtended =
        "androidx.compose.material:material-icons-extended:${Version.compose}"
    const val composeActivity = "androidx.activity:activity-compose:${Version.composeActivity}"
    const val composeLifecycleViewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.composeLifecycleViewmodel}"
    const val composeAccompanistCoil =
        "com.google.accompanist:accompanist-coil:${Version.composeAccompanistCoil}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Version.compose}"

    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}"

    // tests
    const val testJunit = "junit:junit:${Version.testJunit}"
    const val testAndroidxRules = "androidx.test:rules:${Version.androidxTest}"
    const val testAndroidxRunner = "androidx.test:runner:${Version.androidxTest}"
    const val testAndroidxExtJunit = "androidx.test.ext:junit:${Version.androidxTestExt}"
    const val testAndroidxEspresso = "androidx.test.espresso:espresso-core:${Version.testEspresso}"
}

object LibGroup {
    private val composeBasic = listOf(
        Lib.composeUi, Lib.composeFoundation, Lib.composeMaterial, Lib.composeActivity
    )
    private val composeIconsViewModelCoil = listOf(
        Lib.composeIconsCore, Lib.composeIconsExtended, Lib.composeLifecycleViewmodel,
        Lib.composeAccompanistCoil
    )
    val composeAll = composeBasic + composeIconsViewModelCoil
    val composeDebug = listOf(Lib.composeUiTooling, Lib.kotlinReflect)

    val testAndroid = listOf(
        Lib.testAndroidxRules, Lib.testAndroidxExtJunit, Lib.testAndroidxEspresso
    )
}

// Project modules
