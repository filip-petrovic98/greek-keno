plugins {
    alias(libs.plugins.greek.keno.android.library)
    alias(libs.plugins.greek.keno.android.library.compose)
}

android {
    namespace = "com.mozzartbet.greekkeno.core.ui"
}

dependencies {
    implementation(libs.bundles.compose)
}
