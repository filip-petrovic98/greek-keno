pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GreekKeno"
include(":app")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":core:common")
include(":core:data")
include(":core:network")
include(":core:model")
include(":core:testing")
include(":core:ui")
include(":feature:game")

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))