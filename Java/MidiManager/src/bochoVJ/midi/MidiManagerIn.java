/**
 * Wrapper for javax.sound.midi
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.midi;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
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
public class MidiManagerIn implements Receiver{

    Transmitter transmitter;

    List<IMidiVisualizer> visualizers;

    public List<IMidiVisualizer> getVisualizers()
    {
	return this.visualizers;
    }

    public void addVisualizer(IMidiVisualizer vis)
    {
	this.visualizers.add(vis);
    }

    public void setVisualizers(List<IMidiVisualizer> visualizers)
    {
	this.visualizers = visualizers;
    }

    public MidiManagerIn()
    {
	this.visualizers = new LinkedList<IMidiVisualizer>();
    }

    public void startDevice(int deviceNumber) throws MidiUnavailableException
    {
	MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
	MidiDevice InputDevice = MidiSystem.getMidiDevice(aInfos[deviceNumber]);
	InputDevice.open();
	transmitter = InputDevice.getTransmitter();

	transmitter.setReceiver(this);	
    }

    public void startEmulation()
    {
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		while(true)
		{
		    try {
			Thread.sleep(500);
			for(IMidiVisualizer visualizer : visualizers)
			{
			    int note = new Random().nextInt(127);
			    int intensity = new Random().nextInt(127);
			    visualizer.visualizeNote(note, intensity);
			}
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	}).start();
    }

    public void send(MidiMessage message, long timeStamp) 
    {
	ShortMessage sm = (ShortMessage) message;
	if(sm instanceof ShortMessage)
	{
	    switch(sm.getCommand())
	    {   
	    case ShortMessage.NOTE_ON:
		int note = sm.getData1();
		int intensity = sm.getData2();
		for(IMidiVisualizer visualizer : this.visualizers)
		{
		    visualizer.visualizeNote(note, intensity);
		}
		break;
	    case ShortMessage.CHANNEL_PRESSURE:
		break;
	    case ShortMessage.CONTROL_CHANGE:
		break;
	    case ShortMessage.NOTE_OFF:
		break;
	    case ShortMessage.ACTIVE_SENSING:
		break; 
	    case ShortMessage.PROGRAM_CHANGE:
		break;
	    default:
		break;
	    }
	}
	else
	{
	    System.out.println("Strange stuff");
	}   		
    }

    @Override
    public void close() 
    {
	transmitter.close();
    }

}

