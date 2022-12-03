
# Create Flatpak

```bash
# generate 'flatpak/gradle-sources.json'
ci/flatpak-generate-gradle-sources.sh

# create and run flatpak distribution
ci/flatpak-create.sh
```

# Flatpak and gradle

Flatpak builder runs gradle inside a container with restrictive internet access.
Downloading of dependencies is not possible.
[Maven/Gradle support](https://github.com/flatpak/flatpak-builder-tools/issues/37)

Solution:
[Run Gradle generator script](https://github.com/flatpak/flatpak-builder-tools/pull/276)

# Flatpak commands

```bash
# Open shell inside runtime
flatpak run org.gnome.Sdk//43

```

# Flatpak examples

https://github.com/flathub/org.gnome.gitlab.somas.Apostrophe/blob/master/org.gnome.gitlab.somas.Apostrophe.json
https://github.com/flathub/org.ghidra_sre.Ghidra
