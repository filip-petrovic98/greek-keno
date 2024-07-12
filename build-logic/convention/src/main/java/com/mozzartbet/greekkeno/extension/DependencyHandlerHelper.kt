package com.mozzartbet.greekkeno.extension

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}

fun DependencyHandler.testImplementation(dependencyNotation: Any) {
    add("testImplementation", dependencyNotation)
}

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any) {
    add("androidTestImplementation", dependencyNotation)
}

fun DependencyHandler.ksp(dependencyNotation: Any) {
    add("ksp", dependencyNotation)
}

fun DependencyHandler.commonMainApi(dependencyNotation: Any) {
    add("commonMainApi", dependencyNotation)
}

fun DependencyHandler.runtimeOnly(dependencyNotation: Any) {
    add("runtimeOnly", dependencyNotation)
}