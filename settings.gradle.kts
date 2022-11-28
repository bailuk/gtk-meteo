pluginManagement {
    repositories {
        gradlePluginPortal()
        flatDir { // precached artifacts for flatpak build TODO change name
            dirs("local_maven")
        }
    }
}

rootProject.name = "gtk-meteo"
