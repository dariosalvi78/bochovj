MidiWii
_______

MidiWii is a small application that translates the messages sent by a WiiMote into Midi messages.

Particularly buttons are translated to notes and accelerations to controls.


Things you can do with MidiWii:

- control some of your Resolume commands with the wii mote

- play some notes with the wii mote

- control some effects on audio and/or video by moving the wii mote


Requisites
-------------------------

On Windows a proper bluetooth stack supporting L2CAP is required. If your bluetooth adapter supports it, download the Widcomm stack [here](http://www.broadcom.com/support/bluetooth/update.php).


Usage
------

Once started, first of all you have to choose a Midi output interface, for sending the midi messages locally into your machine you can install the free [MidiYoke](http://www.midiox.com/index.htm?http://www.midiox.com/myoke.htm), a little program that routes midi messages internally into your PC.

The selection of the midi out interface

https://bochovj.googlecode.com/svn/trunk/Java/MidiWii/docs/MidiSelect.jpg


Then the main program starts:

https://bochovj.googlecode.com/svn/trunk/Java/MidiWii/docs/MainWindow.jpg


Before pressing the Start button you have to put the WiiMote into [discoverable mode](http://wiki.wiimoteproject.com/Connecting), this is done either by pressing the 1 & 2 buttons simultaneously (soft syncing), or pressing the red SYNC button inside the battery compartment (hard syncing).


When the connection is done you will see plotting the accelerometers data on the little graph and the last button you have pressed at its right.
Buttons and accelerations are mapped to a default configuration of midi channels, you can change it by pressing the Configure button and the following window will appear:

https://bochovj.googlecode.com/svn/trunk/Java/MidiWii/docs/ConfigDialog.jpg

The midi configuration window
