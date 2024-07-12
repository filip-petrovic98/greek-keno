import com.mozzartbet.greekkeno.extension.libs
import com.mozzartbet.greekkeno.extension.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidUnitTestConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        with(project) {
            dependencies {
                testImplementation(libs.findBundle("unit-test").get())
            }
        }
    }
}