plugins {
    alias(libs.plugins.greek.keno.android.feature)
    alias(libs.plugins.greek.keno.android.library.compose)
    alias(libs.plugins.greek.keno.android.unit.test)
}

android {
    namespace = "com.mozzartbet.greekkeno.feature.game"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.testing)
    implementation(projects.core.ui)
}
