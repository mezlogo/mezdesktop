#!/usr/bin/env bash

dm="$DESKMANAGER_EXEC"

if [ -z "$dm" ]; then
    echo "please, set env var DESKMANAGER_EXEC to be deskmanager executable plus handler dir setted"
fi

$dm --profile-name 01_ext_env.txt --feature-dir . install

source "$HOME/.theprofile"

$dm --profile-name 02_env_bin_and_npm.txt --feature-dir . install

echo "logout or source ~/.theprofile"
