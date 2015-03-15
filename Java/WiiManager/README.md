WiiManager project is a wrapper for java libraries for the WiiMote.

It defines a manager that connects to the device and receives inputs, and a handler for those inputs.

Currently two libraries are wrapped WiiRemoteJ and WiiuseJ.

WiiuseJ
-----
--
See: http://code.google.com/p/wiiusej/


WiiRemoteJ
----------

WiiRemoteJ is java library that implements the WiiMote protocol.
It has been developed by Michael Diamond, aka Cha0s, thanks Micheal !

A nice video introduction to the library can be seen here:
http://www.dailymotion.com/video/x1hrfl_wiiremotej_tech

The library is publicly available on Micheal's website here:
http://www.world-of-cha0s.hostrocket.com/WiiRemoteJ/

Please download the full library with sources and documentation if you want to use it outside this implementation.

I have added the binary file (WiiRemoteJ.jar) in Libraries directory, the version I'm using is the 1.6, the latest one I think.


### Requirements

The library requires a an implementation of JSR082, the bluetooth abstraction for Java. Its requirements are quite heavy in terms of the capabilities of the Bluetooth stack.

There are plenty of JSR082 implementations, but I have preferred an open source one, that seems to be quite advanced and compatible with many operating systems and stacks:
http://bluecove.org/

The real pain comes when having to cope with the actual OS and bluetooth stack. My experience is limited to this two cases:


### Windows Vista 32 bits

I am currently using a Belkin USB Bluetooth dongle.
An overview about Bluetooth stacks in Windows is available [here](http://www.wiimoteproject.com/bluetooth-and-connectivity-knowledge-center/a-summary-of-windows-bluetooth-stacks-and-their-connection/)


I have tried the standard Windows, Toshiba and Bluesoleil bluetooth stacks, the bluecove library gets them, but they are too limited to work with WiiRemoteJ.
It seems that only the Widcomm stack has all the capabilities that are required. You can find Widcomm (free) driver here:
http://www.broadcom.com/support/bluetooth/update.php

If you want to install it, be sure that you bluetooth hardware is supported by this driver.


### Windows 7, 64 bits

I have tried to install the Widcomm stack several times, following different suggestions, blogs, fora... nothing worked.
The standard Windows Bluetooth stack is recognised by the Bluecove library, although I had to find for 64 bits compatible version, which wasn't an easy task, but it's officially in the repository now. But unfortunately the WiiRemoteJ does not work with that stack.

I haven't tried on linux nor MacOs, but is seems that the task is easier.

### Capabilities

The library is pretty powerful and supports all the functionalities offered by the hardware.

A good tutorial about how to use it is here:
http://sdpagent.blogspot.com/2009/07/getting-started-with-wiiremotej.html

In my first implementations I just use the accelerometers inputs and the buttons.
