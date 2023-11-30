# if user loged without sourcing $HOME/.profile this file called, cos' there is not ZDOTDIR env setted.
# for instance loged by tty2 withoud Display Manager (LightDM)
source "$HOME/.shellenv"
