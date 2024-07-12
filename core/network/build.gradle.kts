plugins {
    alias(libs.plugins.greek.keno.android.library)
    alias(libs.plugins.greek.keno.android.unit.test)
}

android {
    namespace = "com.mozzartbet.greekkeno.core.network"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)

    implementation(libs.bundles.retrofit)
}
