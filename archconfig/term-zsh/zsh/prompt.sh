#!/usr/bin/env

precmd() {
  GIT_BRANCH=$(git rev-parse --abbrev-ref HEAD 2> /dev/null)
}

function zle-line-init zle-keymap-select {
  PROMPT="%F{yello}$GIT_BRANCH%F{blue}%~"$'\n'"%(?.%F{green}.%F{red})%B${${KEYMAP/vicmd/N}/(main|viins)/I}>%b%f "
  zle reset-prompt
}

zle -N zle-line-init
zle -N zle-keymap-select
