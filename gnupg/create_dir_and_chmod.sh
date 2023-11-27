#!/usr/bin/env bash

command="$1"

case "$1" in
  install)
    mkdir -p $HOME/.gnupg
    chmod 700 $HOME/.gnupg
    ;;

  *)
    echo "given command: [$command] is not supported"
    ;;
esac
