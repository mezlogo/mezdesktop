#!/usr/bin/env bash

command="$1"

case "$1" in
  install)
    mkdir -p $HOME/.ssh
    chmod 700 $HOME/.ssh
    ;;

  *)
    echo "given command: [$command] is not supported"
    ;;
esac
