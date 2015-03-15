Introduction
------------

I have bought a [RFduino board](http://www.rfduino.com/) some time ago and I really think it's a cool project.
The board comes with some example applications for iOS, but since I have an Android phone (and I hate Apple), I had to make my apps myself.


Project
-------

RFduino is a small app, all contained in one class, that controls the RGB LED of the [RGB pushbutton shield](http://www.rfduino.com/rfd22122-rgb-pushbutton-shield-accessory-board.html) of RFduino. On RFduino you need to load the ColorWheel example sketch to make it work.

This simple application shows some basic functionalities of the Bluetooth LE API of Android: discovery of devices, services discovery, connection and sending data.
I have programmed it while I was learning both Android and BLE so it's probable that there are some errors and some naiveties.