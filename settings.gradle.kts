pluginManagement {
    repositories {
        gradlePluginPortal()
        maven ("local_maven")
        flatDir { // precached artifacts for flatpak build TODO change name
            dirs("local_maven")
        }
    }
}

rootProject.name = "gtk-meteo"
