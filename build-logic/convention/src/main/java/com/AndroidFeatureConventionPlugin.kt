import com.android.build.gradle.LibraryExtension
import com.mozzartbet.greekkeno.extension.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply {
                apply("greek.keno.android.library")
                apply("greek.keno.android.hilt")
                apply("greek.keno.android.ktlint")
                apply("kotlin-parcelize")
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                buildTypes {
                    debug {}
                    getByName("staging") {}
                    release {}
                }
            }

            dependencies {
                implementation(project(":core:common"))
                implementation(project(":core:ui"))
            }
        }
    }
}
