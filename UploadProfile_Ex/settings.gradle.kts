pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

        maven {
            url = uri ("https://maven.google.com/")
            name = "Google"
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.google.com/")
            name = "Google"
        }
    }
}

rootProject.name = "UploadProfile_Ex"
include(":app")
 