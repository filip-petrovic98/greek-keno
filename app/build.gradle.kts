plugins {
    alias(libs.plugins.greek.keno.android.application)
    alias(libs.plugins.greek.keno.android.application.compose)
    alias(libs.plugins.greek.keno.android.hilt)
    alias(libs.plugins.greek.keno.android.unit.test)
}

android {
    namespace = "com.mozzartbet.greekkeno"
}

dependencies {

    implementation(projects.core.ui)
    implementation(projects.core.data)
    implementation(projects.core.common)
    implementation(projects.feature.game)

    implementation(libs.androidx.core.splashscreen)
}
