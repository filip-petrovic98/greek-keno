plugins {
    alias(libs.plugins.greek.keno.android.library)
    alias(libs.plugins.greek.keno.android.unit.test)
}

android {
    namespace = "com.mozzartbet.greekkeno.core.testing"
}

dependencies {
    implementation(libs.bundles.unit.test)
}