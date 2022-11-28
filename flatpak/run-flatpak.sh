#!/bin/sh

# podman run -d --name squid-container -e TZ=UTC -p 3128:3128 docker.io/ubuntu/squid:5.2-22.04_beta

export https_roxy=http://localhost:3128/
export http_roxy=http://localhost:3128/

flatpak install flathub org.freedesktop.Platform//22.08 org.freedesktop.Sdk//22.08 org.freedesktop.Sdk.Extension.openjdk11/x86_64/22.08
flatpak-builder --force-clean   build ch.bailu.gtk-meteo.json
