#!/bin/sh
#
# Compile, install and run app locally for logged in user
# Example usage: ./install.sh
#

test -d gradle || cd ..

echo "Install for $(whoami)"

app="gtk-meteo"
desktop="${HOME}/.local/share/applications/ch.bailu.${app}.desktop"
data="${HOME}/.config/${app}"

./gradlew build || exit 1

test -d ${data} || mkdir ${data} || exit 1
cp  build/libs/${app}-all.jar "${data}/${app}.jar"  || exit 1
cp  src/main/resources/svg/app-icon.svg "${data}/${app}.svg" || exit 1

cat > ${desktop} << EOF
[Desktop Entry]
Type=Application
Terminal=false
Exec=java -jar ${data}/${app}.jar
Name=GTK Meteo
Comment=Select location from map and show weather forecast
Icon=${data}/${app}.svg
EOF

chmod 700 "${desktop}" || exit 1
java -jar "${data}/${app}.jar" || exit 1

exit 0
