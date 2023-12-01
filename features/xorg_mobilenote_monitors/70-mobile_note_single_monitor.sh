#!/bin/sh

external_display="$(xrandr -q | grep ' connected ' | grep -v 'eDP' | awk '{ print $1 }')"
inner_display="$(xrandr -q | grep ' connected ' | grep 'eDP' | awk '{ print $1 }')"

if [ -n "$external_display" ] && [ -n "$inner_display" ]; then
  xrandr --auto --output "$externaml_display" --primary --output "$inner_display" --off
fi

