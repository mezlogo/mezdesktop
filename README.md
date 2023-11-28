# mezdesktop

The aim of project is to define self-described features of my linux desktop.
I use ArchLinux all the time, but evolving and maintaincing it such a burden, that I created a `deskmanager` tool for execution of shell scripts and another type of programms, for more info goto https://github.com/mezlogo/deskmanager

## core feature set

Under `core` I assume creating extension points:

1. `ext_env` is a profile.d like framework for source eveything related with environment and especially PATH
It expected to be executad first
It exports DESKM_EXT_ENV_DIR variable for using inside other features and scripts

2. `env_add_bin` creates ~/bin dir for user's executables
It exports DESKM_ENV_BIN_DIR variable for using inside other features

3. `env_add_npm` sets npm local prefix AND adds npm's bin dir to path 

## how to start from scratch

1. install pikaur, openssh, git, nodejs and npm packages
2. clone `https://github.com/mezlogo/deskmanager`
2.1. exec `prepare.sh`
3. clone `https://github.com/mezlogo/mezdesktop`
3.1. exec `DESKMANAGER_EXEC="$HOME/repos/deskmanager/deskmanager-cli/bin/deskmanager.js --handler-dir $HOME/repos/deskmanager/deskmanager-contrib/src" ./initcore.sh`
3.2. logout and login
3.3. exec your profile version
