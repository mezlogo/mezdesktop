#!/usr/bin/env bash

command="$1"
current_shell="$(getent passwd $USER | awk -F ':' '{print $7}')"
target_shell=/usr/bin/zsh
default_shell=/usr/bin/bash

case "$1" in
  install)
    if [ "$target_shell" != "$current_shell" ]; then
      echo "Your current shell is: $current_shell"
      echo "Try to change it to: $target_shell"
      chsh $USER -s "$target_shell"
    fi
    ;;
  diff)
    if [ "$target_shell" == "$current_shell" ]; then
      echo "Login shell is alread zsh: [${current_shell}]"
    else
      echo "Login shell is NOT zsh: [${current_shell}]"
    fi
    ;;
  uninstall)
    if [ "$default_shell" != "$current_shell" ]; then
      echo "Your current shell is: $current_shell"
      echo "Try to change it to: $default_shell"
      chsh $USER -s "$default_shell"
    fi
    ;;
  *)
    echo "given command: [$command] is not supported"
    ;;
esac
