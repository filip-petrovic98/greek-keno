plugins {
    alias(libs.plugins.greek.keno.android.library)
    alias(libs.plugins.greek.keno.android.unit.test)
}

android {
    namespace = "com.mozzartbet.greekkeno.core.testing"

    packaging {
        resources {
            excludes += "/META-INF/*"
        }
    }
}

dependencies {
    implementation(libs.bundles.unit.test)
}
