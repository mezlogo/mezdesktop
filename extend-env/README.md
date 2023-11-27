# extend-env

This is the most basic feature, it implements `source all files in .d folder` pattern for:
1. append path
2. set environment variables

Integration support:
- bash login shell: by sourcing `~/.env` from `~/.bash_profile`
- zsh login shell: by sourcing `~/.env` from `~/.zprofile`

How it works:
1. when you login from shell it executes login script (for bash - .bash_profile and for zsh - .zprofile)
2. in login script just source shell independent environment variables stored in separated files inside .theprofile.d folder with sh file extension
