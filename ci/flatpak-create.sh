#!/bin/sh
test -f gradlew || cd ..
test -f gradlew || exit 1

cd flatpak || exit 1
flatpak install flathub org.freedesktop.Platform//22.08 org.freedesktop.Sdk//22.08 org.freedesktop.Sdk.Extension.openjdk11/x86_64/22.08
flatpak-builder --force-clean build ch.bailu.gtk-meteo.json
cd ..
