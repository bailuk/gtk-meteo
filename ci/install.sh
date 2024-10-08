#!/bin/sh

app_name="gtk-meteo"
app_id="ch.bailu.gtk_meteo"
help="Installs '${app_name}' with id '${app_id}' for logged in user or for user on ssh host
Usage: ./install.sh [--build] [--run] [--no-install] [--dry] [--help] [user@ssh_host]
Options:
  --build: build first
  --run: run after installation
  --no-install: do not install
  --dry: only print commands to console
  user@ssh_host: install on a remote device"

# arguments
option_install="--local"
dry=""
for i in "$@"; do
  if [ "$i" = "--help" ]; then
    echo "$help"
    exit 0
  elif [ "$i" = "--run" ]; then
    option_run="$i"
  elif [ "$i" = "--build" ]; then
    option_build="$i"
  elif [ "$i" = "--no-install" ]; then
    option_install="$i"
  elif [ "$i" = "--dry" ]; then
    dry="echo"
  elif [ "$i" = "--system" ]; then
    option_install="$i"
  else
    target="$i"
  fi
done

if [ "${option_install}" = "--local" ]; then
  if [ -n "$target" ]; then
    option_install="--remote"
  elif [ "$(id -u)" = "0" ]; then
    option_install="--system"
  fi
fi

# source from distribution
src_jar="${app_name}-all.jar"
src_meta="${app_id}.metainfo.xml"
src_desktop="${app_id}.desktop"
src_icon="app-icon.svg"

# source from build
if [ ! -f $src_jar ]; then
  src_jar="build/libs/${src_jar}"
  src_icon="src/main/resources/svg/${src_icon}"
  src_meta="flatpak/${src_meta}"
  src_desktop="flatpak/${src_desktop}"
  test -d gradle || cd ..
  test -d gradle || cd ..
fi


# installation type
if [ "${option_install}" = "--remote" ]; then
  echo ">> install on '$target'"
  home=$(ssh $target echo '$HOME')
  xdg_data_home=$(ssh $target echo '$XDG_DATA_HOME')
  cmd="ssh $target"
  copy="scp"
  tor="$target:"
else
  if [ "${option_install}" = "--system" ]; then
    echo ">> install system wide"

  elif [ "${option_install}" = "--local" ]; then
    echo ">> install for '$(whoami)'"
    home=$HOME
    xdg_data_home=$XDG_DATA_HOME
  fi

  cmd="/bin/sh -c"
  copy="cp -v"
  tor=""
fi

# set directory path for icon, desktop-file and jar
if [ "$xdg_data_home" = "" ]; then
  xdg_data_home="$home/.local/share"
fi

if [ "${option_install}" = "--system" ]; then
  path_cmd_jar="/usr/share/${app_name}"
  path_jar="${target}${path_cmd_jar}"
  path_icon="${target}/usr/share/icons/hicolor/scalable/apps"
  path_desktop="${target}/usr/share/applications"
  path_meta="${target}/usr/share/metainfo"
else
  path_cmd_jar="${home}/.config/${app_name}"
  path_jar="${path_cmd_jar}"
  path_icon="${xdg_data_home}/icons/hicolor/scalable/apps"
  path_desktop="${xdg_data_home}/applications"
fi

# set destination file-paths
dst_desktop="${path_desktop}/${app_id}.desktop"
dst_icon="${path_icon}/${app_id}.svg"
dst_jar="${path_jar}/${app_name}.jar"
dst_meta=${path_meta}/${app_id}.metainfo.xml
java_cmd="java -jar ${path_cmd_jar}/${app_name}.jar"

# build
if [ "${option_build}" = "--build" ]; then
    echo ">> build"
    $dry ./gradlew build                                          || exit 1
fi

# install
if [ "${option_install}" != "--no-install" ]; then
  if [ "${option_install}" = "--system" ]; then
    $dry $cmd "mkdir -p ${path_meta}"
    $dry $copy ${src_meta} "${tor}${dst_meta}"                    || exit 1
  fi

  $dry $cmd "mkdir -p ${path_icon}"                               || exit 1
  $dry $cmd "mkdir -p ${path_desktop}"                            || exit 1
  $dry $cmd "mkdir -p ${path_jar}"                                || exit 1
  $dry $copy $src_jar "${tor}${dst_jar}"                          || exit 1
  $dry $copy $src_icon "${tor}${dst_icon}"                        || exit 1
  $dry $copy $src_desktop "${tor}${dst_desktop}"                  || exit 1
  $dry $cmd "sed -i 's+Exec.*+Exec=${java_cmd}+' ${dst_desktop}"  || exit 1
  $dry $cmd "chmod 755 ${dst_desktop}"                            || exit 1
fi

# run
if [ "$option_run" = "--run" ]; then
  echo ">> run '$java_cmd'"
  $dry $cmd "$java_cmd"                                           || exit 1
fi

exit 0
