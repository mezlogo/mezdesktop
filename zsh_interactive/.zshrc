for profile in "${DESKM_ZSH_DIR}"/*.zsh(.N); do
    test -r "$profile" && source "$profile"
done
unset profile
