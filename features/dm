#!/usr/bin/env bash
hostname="$(cat /etc/hostname)"

exec $HOME/repos/deskmanager/deskmanager-cli/bin/deskmanager.js --handler-dir $HOME/repos/deskmanager/deskmanager-contrib/src --feature-dir . --profile-name "${hostname}.txt" "$1"
