import com.android.build.api.dsl.ApplicationExtension
import com.mozzartbet.greekkeno.base.ProjectConfig
import com.mozzartbet.greekkeno.base.GreekKenoBuildType
import com.mozzartbet.greekkeno.extension.configureAndroidCompose
import com.mozzartbet.greekkeno.extension.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("greek.keno.android.ktlint")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = ProjectConfig.APP_ID
                    minSdk = ProjectConfig.MIN_SDK
                    compileSdk = ProjectConfig.COMPILE_SDK
                    targetSdk = ProjectConfig.TARGET_SDK
                    versionCode = 1
                    versionName = ProjectConfig.VERSION_NAME
                }

                buildFeatures {
                    buildConfig = true
                }

                packaging {
                    resources {
                        excludes += "/META-INF/*"
                    }
                }

//                signingConfigs {
//                    create("staging") {
//                        keyAlias = gradleLocalProperties(rootDir, providers).getProperty("keyAlias")
//                        keyPassword =
//                            gradleLocalProperties(rootDir, providers).getProperty("keyPassword")
//                        storeFile = file(gradleLocalProperties(rootDir, providers)["storeFile"]!!)
//                        storePassword =
//                            gradleLocalProperties(rootDir, providers).getProperty("storePassword")
//                    }
//                    create("release") {
//                        keyAlias = gradleLocalProperties(rootDir, providers).getProperty("keyAlias")
//                        keyPassword =
//                            gradleLocalProperties(rootDir, providers).getProperty("keyPassword")
//                        storeFile = file(gradleLocalProperties(rootDir, providers)["storeFile"]!!)
//                        storePassword =
//                            gradleLocalProperties(rootDir, providers).getProperty("storePassword")
//                    }
//                }

                buildTypes {
                    debug {
                        isDebuggable = true
                        applicationIdSuffix = GreekKenoBuildType.DEBUG.applicationIdSuffix
                        resValue("string", "app_name", GreekKenoBuildType.DEBUG.appName)
                    }
                    create("staging") {
                        isDebuggable = false
                        isMinifyEnabled = false
                        applicationIdSuffix = GreekKenoBuildType.STAGING.applicationIdSuffix
                        resValue("string", "app_name", GreekKenoBuildType.STAGING.appName)
//                        signingConfig = signingConfigs.getByName("staging")
                    }
                    release {
                        isDebuggable = false
                        isMinifyEnabled = false
                        resValue("string", "app_name", GreekKenoBuildType.RELEASE.appName)
//                        signingConfig = signingConfigs.getByName("release")
                    }
                }

                configureKotlinAndroid(this)
                configureAndroidCompose(this)
            }
        }
    }
}
