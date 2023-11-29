#!/usr/bin/env bash

command="$1"
target_groups=(video)
installed_groups="$(id -nG $USER)"

case "$1" in
  install)
    ;;

  *)
    echo "given command: [$command] is not supported"
    ;;
esac
