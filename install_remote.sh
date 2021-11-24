#!/bin/sh
#
# Compile, install and run app on a remote host via ssh for a specific user
# Example usage: ./install_remote.sh user@mobian
#

if [ $# -eq 0 ]
  then
    echo "Example usage: ./install_remote.sh user@mobian"
    exit
fi


remote=$1

echo "Install to ${remote}"

home=$(ssh $remote pwd)
app="gtk-meteo"
desktop="${home}/.local/share/applications/ch.bailu.${app}.desktop"
data="${home}/.config/${app}"

./gradlew fatJar || exit 1

ssh $remote "test -d ${data} || mkdir ${data}" || exit 1
scp  build/libs/${app}.jar "${remote}:${data}/${app}.jar"  || exit 1
scp  src/main/resources/icon.svg "${remote}:${data}/${app}.svg" || exit 1

ssh "${remote}" "cat > ${desktop}" << EOF
[Desktop Entry]
Version=1.0
Type=Application
Terminal=false
Exec=java -jar ${data}/${app}.jar
Name=GTK Meteo
Comment=Select location from map and show weather forecast 
Icon=${data}/${app}.svg
EOF

ssh $remote chmod 700 "${desktop}" || exit 1
ssh -X $remote java -jar "${data}/${app}.jar" || exit 1

exit 0

