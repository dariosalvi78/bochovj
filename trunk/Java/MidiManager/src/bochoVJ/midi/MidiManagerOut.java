/**
 * Wrapper for javax.sound.midi
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.midi;

import java.util.Random;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

/**
 * Wrapper for the Java Midi library
 * @author bochovj
 *
 */
public class MidiManagerOut implements Transmitter{

    Receiver receiver;

    public MidiManagerOut()
    {
    }

    @Override
    public Receiver getReceiver() {
	return this.receiver;
    }

    @Override
    public void setReceiver(Receiver receiver) {
	this.receiver = receiver;
    }

    public void sendControlChange(int channel, int controller, int value) throws InvalidMidiDataException 
    {
	ShortMessage msg = new ShortMessage();
	msg.setMessage(ShortMessage.CONTROL_CHANGE, channel, controller, value);
	this.receiver.send(msg, -1);
    }

    public void sendNoteChange(int channel, int note, int velocity) throws InvalidMidiDataException
    {
	ShortMessage msg = new ShortMessage();
	msg.setMessage(ShortMessage.NOTE_ON, channel, note, velocity);
	this.receiver.send(msg, -1);
    }

    public void startDevice(int deviceNumber) throws MidiUnavailableException
    {
	MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
	MidiDevice device = MidiSystem.getMidiDevice(aInfos[deviceNumber]);
	device.open();
	Receiver r = device.getReceiver();

	this.setReceiver(r);	
    }

    public void startEmulation()
    {
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		while(true)
		{
		    try 
		    {
			for(int i = 0; i<128; i++)
			{
			    //Send note
			    int note = new Random().nextInt(127);
			    int intensity = new Random().nextInt(127);
			    sendNoteChange(1, note, intensity);
			    //Send control
			    sendControlChange(1, 16, i);
			    Thread.sleep(1000);
			}
		    } 
		    catch (Exception e) 
		    {
			e.printStackTrace();
		    }
		}
	    }
	}).start();
    }

    @Override
    public void close() {
	receiver.close();
    }


    /**
     * Just a testing application that sends values each second
     * @param args
     */
    public static void main(String[] args)
    {
	try
	{
	    MidiManagerOut mng = new MidiManagerOut();

	    mng.startDevice(11);
	    mng.startEmulation();
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }
}

