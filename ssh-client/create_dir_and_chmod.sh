#!/usr/bin/env bash

command="$1"

case "$1" in
  install)
    mkdir -p ~/.ssh
    chmod 700 ~/.ssh
    ;;

  *)
    echo "given command: [$command] is not supported"
    ;;
esac
