#!/usr/bin/env bash

command="$1"

case "$1" in
  install)
    GROUP="video"
    if id -nG "$USER" | grep -qw "$GROUP"; then
        sudo usermod -aG $GROUP $USER
    fi
    ;;

  *)
    echo "given command: [$command] is not supported"
    ;;
esac
