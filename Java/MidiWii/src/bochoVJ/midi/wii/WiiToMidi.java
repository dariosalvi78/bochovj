package bochoVJ.midi.wii;

import javax.sound.midi.InvalidMidiDataException;

import bochoVJ.midi.MidiManagerOut;
import bochoVJ.wii.IWiiHandler;
import bochoVJ.wii.IWiiManager;
import bochoVJ.wii.WiiuseJManager;
import bochoVJ.wii.IWiiHandler.Acceleration;

public class WiiToMidi {

	public MidiManagerOut midiOut;

	public IWiiManager wiimng;

	int mididevice;

	Configuration config;

	private boolean muteAccX;
	private boolean muteAccY;
	private boolean muteAccZ;
	
	private double lastX = 0;
	private double lastY = 0;
	private double lastZ = 0;
	
	public enum TransformationFunction { ABS, LINEAR };

	public TransformationFunction transFunction;

	public Configuration getCurrentConfig() {
		return config;
	}

	public WiiToMidi(int mididevice) throws Exception {
		muteAccX = false;
		muteAccY = false;
		muteAccZ = false;

		transFunction = TransformationFunction.ABS;

		config = Configuration.defaultConfiguration();

		this.mididevice = mididevice;

		midiOut = new MidiManagerOut();

		//wiimng = new WiiRemoteJManager();
		wiimng = new WiiuseJManager();
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
					if(!muteAccX)
						midiOut.sendControlChange(config.accsChannel, config.accx, accToControl(acc.x));

					if(!muteAccY)
						midiOut.sendControlChange(config.accsChannel, config.accy, accToControl(acc.y));

					if(!muteAccZ)
						midiOut.sendControlChange(config.accsChannel, config.accz, accToControl(acc.z));
					
					double energy = (Math.abs(acc.x - lastX) + Math.abs(acc.y - lastY) + Math.abs(acc.z- lastZ)) / 3;
					lastX = acc.x;
					lastY = acc.y;
					lastZ = acc.z;
					int energyControl = normalizeControl( (int)(energy / Acceleration.MaxACC * 200));
					midiOut.sendControlChange(config.accsChannel, config.energy , energyControl);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}

	public void setAccsFunction(TransformationFunction fc){
		transFunction = fc;
	}


	public void muteAccX(boolean muted) {
		muteAccX = muted;
	}
	
	public boolean isMutedAccX() {
		return muteAccX;
	}

	public void muteAccY(boolean muted) {
		muteAccY = muted;
	}
	
	public boolean isMutedAccY() {
		return muteAccY;
	}

	public void muteAccZ(boolean muted) {
		muteAccZ = muted;
	}
	
	public boolean isMutedAccZ() {
		return muteAccZ;
	}


	public int accToControl(double v) {
		int val = 0;

		if(transFunction == TransformationFunction.ABS)
			val =(int)(Math.abs(v)  * 127/ Acceleration.MaxACC) ;
		else if (transFunction == TransformationFunction.LINEAR)
			val =(int)((v + Acceleration.MaxACC)  * 127/ (2*Acceleration.MaxACC)) ;

		return normalizeControl(val);
	}
	
	private int normalizeControl(int c){
		if(c < 0)
			return 0;
		
		if(c > 127)
			return 127;
		
		return c;
	}

	public void start() throws Exception {
		System.out.println("Starting midi out and wii");

		midiOut.startDevice(mididevice);

		wiimng.connect();
	}

	public void stop() {
		System.out.println("Stopping midi out and wii");

		wiimng.disconnect();

		midiOut.close();
	}

	public void configure(Configuration c) {
		config = c;
		config.saveToFile();
	}
}
