#!/bin/sh
test -f gradlew || cd ..
test -f gradlew || exit 1

cd flatpak || exit 1
mkdir -p build || exit 1

echo "_"
echo "Install dependencies"
flatpak install flathub org.gnome.Platform//43 org.gnome.Sdk//43 org.freedesktop.Sdk.Extension.openjdk11/x86_64/22.08 || exit 1

echo "_"
echo "Build flatpak"
flatpak-builder --force-clean build/build ch.bailu.gtk-meteo.json || exit 1

echo "_"
echo "Install flatpak"
echo "flatpak-builder --user --install --force-clean build/build ch.bailu.gtk-meteo.json || exit 1"

# Single-file bundle
# https://unix.stackexchange.com/questions/695934/how-do-i-build-a-flatpak-package-file-from-a-flatpak-manifest
# https://docs.flatpak.org/en/latest/single-file-bundles.html

echo "_"
echo "Export flatpak"
flatpak build-export build/export build/build || exit 1


echo "_"
echo "Build single-file bundle"
flatpak build-bundle build/export build/gtk-meteo.flatpak "ch.bailu.gtk-meteo"

echo "_"
echo "Install single-file bundle"
echo "flatpak install build/gtk-meteo.flatpak"

echo "_"
echo "Run flatpak"
echo "'flatpak run ch.bailu.gtk-meteo || exit 1'"
