import com.android.build.gradle.LibraryExtension
import com.mozzartbet.greekkeno.base.ProjectConfig
import com.mozzartbet.greekkeno.extension.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.library")
                apply("greek.keno.android.hilt")
                apply("org.jetbrains.kotlin.android")
                apply("greek.keno.android.ktlint")
                apply("kotlin-parcelize")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = ProjectConfig.TARGET_SDK
                defaultConfig.minSdk = ProjectConfig.MIN_SDK

                apply {
                    compileSdk = ProjectConfig.COMPILE_SDK
                }

                buildTypes {
                    debug {}
                    create("staging") {}
                    release {}
                }
            }
        }
    }
}
