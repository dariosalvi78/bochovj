/**
 * Wrapper for javax.sound.midi
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

/**
 * @author bochovj
 *
 */
public class MidiManager {
    
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

}
