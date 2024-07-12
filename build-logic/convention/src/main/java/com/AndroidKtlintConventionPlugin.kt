import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidKtlintConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply {
                apply("org.jlleitschuh.gradle.ktlint")
            }

            project.tasks.getByName("preBuild") {
                dependsOn("ktlintFormat")
            }
        }
    }
}
