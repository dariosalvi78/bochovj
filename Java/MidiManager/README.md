Introduction
------------

The MidiManager project is a simple wrapper for the [javax.sound.midi](http://java.sun.com/j2se/1.4.2/docs/api/javax/sound/midi/package-summary.html) library.

It defines an interface that handles midi events sent by a physical midi device, a wrapper to start a midi input and a wrapper to start a midi output.

Moreover it gives a simple GUI (using Swing) to list and select the device from the local computer.

SerialToMidi
------------

is a tool that routes messages coming from a serial port to a midi device.

The messages on the serial port must follow an extremely simple protocol that follows these rules:

  * one message per line (i.e. packets are separated by a carriage return)
  * Control changes are represented as: cc ch 10 ctrl 12 val 130
  * Notes ON are represented as: non ch 10 key 12 vel 120
  * Notes OFF are represented as: noff ch 10 key 14 vel 120
  * Aftertouches are represented as: at ch 10 key 14 tch 100


The library uses the [Java Communications API](http://www.oracle.com/technetwork/java/index-jsp-141752.html) implementation offered by the [RXTX project](http://rxtx.qbang.org/wiki/index.php/Main_Page) which is compatible with all operating systems (I am using the implementation for Windows 7 64 bits).
The standard implementation is much worse and does not support Windows anymore.


Another possibiliy for translating serial messages to MIDI would be using a Serial to Midi driver, which would act at the operating system level.
The most famous driver is the Roland, but is quite old and does not support more recent OSs. A better (IMHO) driver is produced by Yamaha and can be downloaded [http://download.yamaha.com/usb_midi/index.html here].

I have made several tests, but in the end I gave up and I decided to implement my own protocol as it was much more simple and powerful.
