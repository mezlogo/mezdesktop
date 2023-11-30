#!/usr/bin/env

alias ll='ls -alF --color=always'
alias ls='ls -F --color=always'

if command -v nvim &> /dev/null ; then
    alias vim='nvim'
    alias vi='nvim'
fi

if command -v bat &> /dev/null ; then
    alias cat='bat'
fi

yadm_path=$HOME/.dots.git
alias yadm='git --work-tree=$HOME --git-dir=$yadm_path'
repo_pathes="$HOME:$yadm_path $EXO"

alias gst="mygit status $repo_pathes"
alias gimprt="mygit fetch $repo_pathes && mygit rebase $repo_pathes"
alias gexprt="mygit commit $repo_pathes && mygit push $repo_pathes"

alias idea="$HOME/tools/idea/bin/idea.sh"

alias pip='python -m pip'
