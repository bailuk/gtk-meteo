[![Build on debian](https://github.com/bailuk/gtk-meteo/actions/workflows/build-on-debian.yml/badge.svg)](https://github.com/bailuk/gtk-meteo/actions/workflows/build-on-debian.yml)

# GTK Meteo
Weather forecast application based on [mapsforge-gtk](https://github.com/bailuk/mapsforge-gtk) and [java-gtk](https://github.com/bailuk/java-gtk).
Forecast data is from [Meteorologisk institutt Norge](https://www.met.no).

## Features
- Select place from map or text search
- 5 days forecast
- 5 lockable presets
- Optimized for small screens: tested on [PinePhone](https://www.pine64.org/pinephone/) 

## Screenshot
![Screenshot](screenshot.png) 

## Build
- Install `libgtk-4`
- Compile and run this app: `./gradlew run` 
 
 ## License
© Map and data [OpenStreetMap contributors, ODbL 1.0](https://osm.org/copyright)  
© Icons and data [MET Norway](https://api.met.no/doc/License)  
Source code is licensed under the [MIT License](https://en.wikipedia.org/wiki/MIT_License)
