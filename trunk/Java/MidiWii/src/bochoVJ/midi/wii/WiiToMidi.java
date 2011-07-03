package bochoVJ.midi.wii;

import javax.sound.midi.InvalidMidiDataException;

import bochoVJ.midi.MidiManagerOut;
import bochoVJ.wii.IWiiHandler;
import bochoVJ.wii.WiiManager;
import bochoVJ.wii.IWiiHandler.Acceleration;

public class WiiToMidi {
    
    public MidiManagerOut midiOut;
    
    public WiiManager wiimng;
    
    int mididevice;
    
    Configuration config;
    
    public Configuration getCurrentConfig()
    {
	return config;
    }
    
    public WiiToMidi(int mididevice)
    {
	config = Configuration.defaultConfiguration();
	
	this.mididevice = mididevice;
	
	midiOut = new MidiManagerOut();
	
	wiimng = new WiiManager();
	
	wiimng.addHandler(new IWiiHandler() {
	    
	    @Override
	    public void handleButton(WiiButton b) {
		
		    try {
			midiOut.sendNoteOn(config.buttonsChannel, config.getButtonNote(b), 100);
		    } 
		    catch (InvalidMidiDataException e) {
			e.printStackTrace();
		    }
	    }
	    
	    @Override
	    public void handleAcc(Acceleration acc) {
		try{
		    midiOut.sendControlChange(config.accsChannel, config.accx, accToControl(acc.x));
		    
		    midiOut.sendControlChange(config.accsChannel, config.accy, accToControl(acc.y));
		    
		    midiOut.sendControlChange(config.accsChannel, config.accz, accToControl(acc.z));
		}
		catch(Exception ex)
		{
		    ex.printStackTrace();
		}
	    }
	});
    }
    
    private int accToControl(double v)
    {
	int val = (int)(Math.abs(v)  * 127/ Acceleration.MaxACC) ;
	if(val >127)
	    val = 127;
	
	return val;
    }
    
    public void start() throws Exception
    {
	System.out.println("Starting midi out and wii");
	
	midiOut.startDevice(mididevice);
	
	wiimng.connect();
    }

    public void stop()
    {
	System.out.println("Stopping midi out and wii");
	
	wiimng.disconnect();
	
	midiOut.close();
    }
    
   public void configure(Configuration c)
   {
       config = c;
   }
}
