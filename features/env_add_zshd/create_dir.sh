#!/usr/bin/env bash

command="$1"

case "$1" in
  install)
    mkdir -p "$HOME/.zsh.d"
    ;;

  *)
    echo "given command: [$command] is not supported"
    ;;
esac
