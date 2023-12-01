#!/usr/bin/env bash

command="$1"
ideaTarGzPath=$HOME/downloads/idea.tar.gz

case "$1" in
  install)
    if [ ! -f "$ideaTarGzPath" ]; then
      curl -L -o "$ideaTarGzPath" 'https://download-cdn.jetbrains.com/idea/ideaIC-2023.2.5.tar.gz'
    fi
    ;;

  *)
    echo "given command: [$command] is not supported"
    ;;
esac
