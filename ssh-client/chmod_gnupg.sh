#!/usr/bin/env bash

command="$1"

case "$1" in
  install)
    chmod 600 $HOME/.gnupg/gpg-agent.conf
    ;;

  *)
    echo "given command: [$command] is not supported"
    ;;
esac
