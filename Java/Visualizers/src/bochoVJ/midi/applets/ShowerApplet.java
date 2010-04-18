package bochoVJ.midi.applets;

import processing.core.PApplet;
import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.MidiApplet;
import bochoVJ.midi.MidiManagerIn;
import bochoVJ.midi.visualizers.ShowerVisualizer;

public class ShowerApplet extends MidiApplet {
	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", ShowerApplet.class.getName() });
	  }
	
	private IMidiVisualizer visualizer;
	
	public ShowerApplet()
	{
		_instance = this;
		visualizer = new ShowerVisualizer();
	}
	
	public void setup()
	{	
		size(640, 480);
		background(0);
		MidiManagerIn mng = new MidiManagerIn();
		mng.addHanler(visualizer);
		
		//mng.showDevices();
		//mng.startDevice(0);
		mng.startEmulation();
	}

	public void draw(){
		background(0);
		visualizer.draw();
	}

}
