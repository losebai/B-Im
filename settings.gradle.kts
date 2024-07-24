pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        mavenLocal()
        mavenCentral()
        maven ("https://storage.googleapis.com/r8-releases/raw")
        maven("https://jitpack.io")
    }
}


rootProject.name = "B-Im"
include(":app")