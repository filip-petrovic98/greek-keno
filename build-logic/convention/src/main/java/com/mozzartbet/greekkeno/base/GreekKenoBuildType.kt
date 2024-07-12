package com.mozzartbet.greekkeno.base

enum class GreekKenoBuildType(val applicationIdSuffix: String? = null, val appName: String) {
    DEBUG(".debug", "Greek Keno Debug"),
    STAGING(".staging", "Greek Keno Staging"),
    RELEASE(null, "Greek Keno"),
}
