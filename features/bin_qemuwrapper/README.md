# qemuwrapper

## motivation

qemu/kvm is a powerful virtualization tool with only one drawback: there is no simple command builder.

`libvirt` is a generic vm-frontend, it means that it supports not only QEMU, but also another vm technologies.

I want simples as possible bash script with predefined options and help for general usage.

## usage

### use-case basic arch-linux installation

Hardware:
- UEFI: ovmf
- disk: 20gb/qcow2
- net: NAT or bridge
- GPU: std or qxl/spice
- keyboard/mouse

Steps:
- create disk
- run VM with installation source
- run VM without installation source

- shrink disk
- backup disk
- open ssh port: use bridge
- share folder
- create a snapshot
