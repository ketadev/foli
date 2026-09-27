plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "dev.detekt")

    val buildPath = layout.buildDirectory.get().asFile.toPath()
    afterEvaluate {
        tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask>().configureEach {
            val sourceSetName = name.substringAfter("Over", "").substringBefore("SourceSet", "")
            if (sourceSetName.isNotEmpty() && name.endsWith("SourceSet")) {
                val sourceDirectory = sourceSetName.replaceFirstChar { it.lowercase() }
                setSource(fileTree("src/$sourceDirectory") { include("**/*.kt") })
            }
        }
    }
    tasks.withType<dev.detekt.gradle.Detekt>().configureEach {
        exclude { element ->
            element.file.toPath().startsWith(buildPath)
        }
    }
}
