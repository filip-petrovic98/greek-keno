plugins {
    alias(libs.plugins.greek.keno.android.library)
    alias(libs.plugins.greek.keno.android.unit.test)
}

android {
    namespace = "com.mozzartbet.greekkeno.core.data"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.model)
    implementation(projects.core.network)
}
