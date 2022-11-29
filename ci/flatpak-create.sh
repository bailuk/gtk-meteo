#!/bin/sh
test -f gradlew || cd ..
test -f gradlew || exit 1

cd flatpak || exit 1
flatpak install flathub org.gnome.Platform//43 org.gnome.Sdk//43 org.freedesktop.Sdk.Extension.openjdk11/x86_64/22.08 || exit 1
flatpak-builder --force-clean build ch.bailu.gtk-meteo.json || exit 1
flatpak-builder --user --install --force-clean build ch.bailu.gtk-meteo.json || exit 1
flatpak run ch.bailu.gtk-meteo || exit 1
