# Commands
- `flatpak install flathub org.freedesktop.Platform//21.08 org.freedesktop.Sdk//21.08`
- `flatpak-builder --force-clean  build ch.bailu.gtk-meteo.json`

# Flatpak and gradle
Flatpak builder runs gradle inside a container with restrictive internet access.
Downloading of dependencies is not possible.
[Maven/Gradle support](https://github.com/flatpak/flatpak-builder-tools/issues/37)

Solution:
[Run Gradle generator script](https://github.com/flatpak/flatpak-builder-tools/pull/276)
