#!/usr/bin/env bash

dm="$DESKMANAGER_EXEC"

if [ -z "$dm" ]; then
    echo "please, set env var DESKMANAGER_EXEC to be deskmanager executable plus handler dir setted something like following:"
    echo 'DESKMANAGER_EXEC="$HOME/repos/deskmanager/deskmanager-cli/bin/deskmanager.js --handler-dir $HOME/repos/deskmanager/deskmanager-contrib/src" ./initcore.sh'
    exit 1
fi

$dm --profile-name 01_ext_env.txt --feature-dir . install

source "$HOME/.theprofile"

$dm --profile-name 02_env_bin_npm_zsh.txt --feature-dir . install

echo "logout or source ~/.theprofile"
