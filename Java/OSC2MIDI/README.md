This tool allows mapping OSC messages to MIDI ctrl messages.

Start the Jar file (a Java virtual machine must be installed) and:

  * select the MIDI device you want to send MIDI messages to
  * write the number of the port where OSC messages are sent to

https://bochovj.googlecode.com/svn/trunk/Java/OSC2MIDI/docs/OSC2MIDI.jpg

Then you will see a table where the first column is the detected OSC address, the second is the MIDI channel (always 1) and the third is the MIDI crtl number (automatically assigned).
The lower panel contains a graph of the received signals, each OSC address is assigned a random color.

Details
-------

OSC2MIDI translates OSC messages in MIDI messages in this way:

  * All MIDI messages are sent to the same channel (number 1)
  * Every OSC address corresponds to a ctrl number
  * Ctrl number is chosen randomly, but it can be modified in the table of the program
  * Only messages with at least one integer or float argument are converted
  * Only the first argument is converted
  * The actual MIDI value is automatically mapped taking into account the minimum and maximum values received at that address (autoscaling)
  * Autoscaling does not evolve with time, the minimum and maximum values are kept fixed once observed
