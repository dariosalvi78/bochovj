/**
 * Wrapper for javax.sound.midi
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.midi;

import java.awt.Frame;
import java.util.LinkedList;
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

    boolean started;

    Receiver receiver;

    public MidiManagerOut()
    {
	started = false;
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

    public void sendNoteOn(int channel, int note, int velocity) throws InvalidMidiDataException
    {
	ShortMessage msg = new ShortMessage();
	msg.setMessage(ShortMessage.NOTE_ON, channel, note, velocity);
	this.receiver.send(msg, -1);
    }

    public void startDevice(int deviceNumber) throws Exception
    {
	if(started == true)
	    throw new Exception("Cannot start an already started device, first close it");
	
	MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
	System.out.println("Opening OUT device: "+aInfos[deviceNumber].getName());
	
	MidiDevice device = MidiSystem.getMidiDevice(aInfos[deviceNumber]);
	device.open();
	Receiver r = device.getReceiver();

	this.setReceiver(r);	
	started = true;
    }

    public void startEmulation() throws Exception
    {
	if(started == true)
	    throw new Exception("Cannot start an already started device, first close it");

	new Thread(new Runnable() {
	    @Override
	    public void run() {
		started = true;
		while(started)
		{
		    try 
		    {
			for(int i = 0; i<128; i++)
			{
			    //Send note
			    int note = new Random().nextInt(127);
			    int intensity = new Random().nextInt(127);
			    sendNoteOn(1, note, intensity);
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
	started = false;
    }

    public int promptUserSelectDevice()
    {
	LinkedList<MidiDeviceSelectDialog.MidiDevice> devices = new LinkedList<MidiDeviceSelectDialog.MidiDevice>();
	MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
	for (int i = 0; i < aInfos.length; i++) {
	    try {
		MidiDevice device = MidiSystem.getMidiDevice(aInfos[i]);
		if(device.getMaxReceivers() != 0)
		{
		    devices.add(new MidiDeviceSelectDialog.MidiDevice(i, aInfos[i].getName()));
		    System.out.println("" + i + "  "
			    + aInfos[i].getName() + ", " + aInfos[i].getVendor()
			    + ", " + aInfos[i].getVersion() + ", "
			    + aInfos[i].getDescription());
		}
	    }  
	    catch (MidiUnavailableException e) {
		//Ignore device
	    }
	}

	//Generate dialog:
	MidiDeviceSelectDialog dial = new MidiDeviceSelectDialog(null, "Please select Midi OUT device");
	dial.createMidiOutList(devices);
	dial.setDeviceHandler(new MidiDeviceSelectDialog.IDeviceHandler() {
	    @Override
	    public void handle(int deviceNumber) {
		devN = deviceNumber;
		synchronized(mutex)
		{
		    mutex.notify();
		}
	    }
	});
	//dial.setEnabled(true);
	dial.setVisible(true);
	synchronized(mutex)
	{
	    try {
		mutex.wait();
	    } 
	    catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

	return devN;
    }

    private int devN;
    private Object mutex = new Object();
}

