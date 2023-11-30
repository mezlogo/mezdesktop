source "$ZDOTDIR/aliases.sh"
source "$ZDOTDIR/zshaliases.sh"
source "$ZDOTDIR/history.sh"
source "$ZDOTDIR/options.sh"
source "$ZDOTDIR/plugins.sh"
source "$ZDOTDIR/keybinds.sh"
source "$ZDOTDIR/prompt.sh"

stty -ixon

if [ -f "$EXO/config/shell/sourceitrc.sh" ]; then
    source "$EXO/config/shell/sourceitrc.sh"
fi
