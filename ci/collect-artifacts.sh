#!/bin/sh

mkdir -p $1 || exit 1
cp build/libs/* $1 || exit 1
cp ci/install.sh $1 || exit 1
cp src/main/resources/svg/app-icon.svg $1 || exit 1
cp flatpak/*.metainfo.xml $1 || exit 1
cp flatpak/*.desktop $1 || exit 1
