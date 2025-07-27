
# Create Flatpak

```bash
# Clear cache
rm -rf build

# Generate 'gradle-sources.json'
./flatpak-generate-gradle-sources.sh

# Commit changes and push changes
# Pin to commit (update flatpak manifest)
./flatpak-pin-commit.sh 

# Create flatpak
./flatpak-create.sh

# ...Or build install and test flathub-flatpak
./flatpak-create-lint.sh 
```

# Flatpak and gradle

Flatpak builder runs gradle inside a container with restrictive internet access.
Downloading of dependencies is not possible.
[Maven/Gradle support](https://github.com/flatpak/flatpak-builder-tools/issues/37)

Solution:
[Run Gradle generator script](https://github.com/flatpak/flatpak-builder-tools/pull/276)

# Flatpak commands

```bash
# List installed
flatpak list
flatpak list --app
flatpak list --runtime

# Uninstall
flatpak uninstall ch.bailu.gtk_meteo
flatpak uninstall --unused

# Open shell inside runtime
flatpak run org.gnome.Sdk//47
flatpak run --command=/bin/bash ch.bailu.gtk_meteo
export PATH=$PATH:/bin
```

# Flatpak examples

- [Apostrophe](https://github.com/flathub/org.gnome.gitlab.somas.Apostrophe/blob/master/org.gnome.gitlab.somas.Apostrophe.json)
- [Ghidra](https://github.com/flathub/org.ghidra_sre.Ghidra)
