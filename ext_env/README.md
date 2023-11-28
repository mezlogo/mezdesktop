# ext-env

This is the most basic feature, it implements `source all files in .d folder` pattern for:
1. append path
2. set environment variables

Integration support:
- bash login shell: by sourcing `~/.env` from `~/.bash_profile`
- zsh login shell: by sourcing `~/.env` from `~/.zprofile`
