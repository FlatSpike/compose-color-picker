@file:Suppress("UnstableApiUsage")

include(":baselineprofile")


pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "compose-color-picker"

include(
    ":app",
    ":color-picker",
    ":baselineprofile"
)
