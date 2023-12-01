# xorg spice guest and host

- `qxl` is paravirtual graphic driver with 2D support
- `spice` is a video protocol

## Host actions

- choose `qxl` as vga, by doing: `qemu`

## Guest actions

packages:
- `xf86-video-qxl`: xorg driver for qxl device (better 2D performance)
- `spice-vdagent`: adjust resolution, shared clipboard, host mouse support
- `x-resize`: fix resolution adjuster

kernel modules: `qxl`, `bochs_drm`
