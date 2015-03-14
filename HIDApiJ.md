# Objective #

This library implements a set of tools and parsers for HID devices.
It uses a native library called HIDApi which can run on Windows, Linux and MacOS (see http://www.signal11.us/oss/hidapi/)

The current version contains the native library for Windows only, in the future other OS will be supported (ask me to do it and I will be happy to help!).

The native library is mapped to java using [JNA](http://jna.java.net/).


# Devices #

At the moment only the [Minia](http://minitronics.net/) device has been supported.
The code implements a parser of the packets sent by the device and nice GUI for testing it.

![https://bochovj.googlecode.com/svn/trunk/Java/HIDApiJ/docs/MiniaTester.jpg](https://bochovj.googlecode.com/svn/trunk/Java/HIDApiJ/docs/MiniaTester.jpg)