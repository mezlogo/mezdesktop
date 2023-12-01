for profile in "${DESKM_ENV_ZSH_DIR}"/*.zsh; do
    test -r "$profile" && source "$profile"
done
unset profile
