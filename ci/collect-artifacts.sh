#!/bin/sh

mkdir $1 || exit 1
cp build/libs/* $1 || exit 1
cp ci/install.sh $1 || exit 1
cp src/main/resources/svg/app-icon.svg $1 || exit 1
