pluginManagement {
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

rootProject.name = "My Application0317"
include(":app")
include(":ch06_view")
include(":ch06_view2")
include(":ch07_layout")
include(":ch09_resource")
include(":ch08_event")
include(":ch10_dialog")
include(":ch13_intent")
include(":midterm")
