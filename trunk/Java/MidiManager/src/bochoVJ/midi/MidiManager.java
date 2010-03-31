/**
 * Midi abstraction, uses javax.sound.midi
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
 * 
 * @author bochovj
 *
 */
public class MidiManager implements Receiver{

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
	
	public MidiManager()
	{
		this.visualizers = new LinkedList<IMidiVisualizer>();
	}

	public void showDevices()
	{
		MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < aInfos.length; i++) {
			try {
				MidiDevice device = MidiSystem.getMidiDevice(aInfos[i]);
				boolean bAllowsInput = (device.getMaxTransmitters() != 0);
				boolean bAllowsOutput = (device.getMaxReceivers() != 0);
				System.out.println("" + i + "  "
						+ (bAllowsInput ? "IN " : "   ")
						+ (bAllowsOutput ? "OUT " : "    ")
						+ aInfos[i].getName() + ", " + aInfos[i].getVendor()
						+ ", " + aInfos[i].getVersion() + ", "
						+ aInfos[i].getDescription());
			}  
			catch (MidiUnavailableException e) {
				System.out.println("Midi device unavailable"+i);
				System.err.println(e);
			}
		}
	}

	public void startDevice(int deviceNumber)
	{
		try{
			MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
			MidiDevice InputDevice = MidiSystem.getMidiDevice(aInfos[deviceNumber]);
			InputDevice.open();
			Receiver r = MidiSystem.getReceiver();
			Transmitter t = InputDevice.getTransmitter();
			t.setReceiver(r);	

			Receiver r2 = this;
			Transmitter t2 = InputDevice.getTransmitter();
			t2.setReceiver(r2);	
		}
		catch(MidiUnavailableException e)
		{
			System.out.println("Device "+0+" unavailabe");
		}   
	}

	public void startEmulation()
	{
		Thread t = new Thread(new MidiManager.EmulatorThread(this));
		t.start();
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
	public void close() {

	}

	private class EmulatorThread implements Runnable
	{
		MidiManager mngr;

		public EmulatorThread(MidiManager mng)
		{
			this.mngr = mng;
		}

		@Override
		public void run() {
			while(true)
			{
				try {
					Thread.sleep(500);
					for(IMidiVisualizer visualizer : mngr.visualizers)
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
	}
	
	
	public static void main(String[] args)
	{
		MidiManager mng = new MidiManager();
		mng.showDevices();
	}
}

