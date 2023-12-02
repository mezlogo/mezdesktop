#!/bin/sh

external_display="DP-2"
inner_display="DP-4"
isFound=`xrandr -q | grep "$external_display connected "`

if [ -n "$isFound" ]; then
  xrandr --output DP-0 --off --output DP-1 --off --output DP-2 --pos 0x0 --rotate normal --output DP-3 --off --output HDMI-0 --off --output DP-4 --off --output None-1-1 --off
fi
