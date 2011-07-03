package bochoVJ.midi.applets;

import java.util.LinkedList;

import processing.core.PApplet;
import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.MidiApplet;
import bochoVJ.midi.MidiManagerIn;
import bochoVJ.midi.visualizers.OrnamentalTreeVisualizer;

public class OrnamentalTreeApplet extends MidiApplet {

	private IMidiVisualizer visualizer;
	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", OrnamentalTreeApplet.class.getName() });
	  }
	
	
	public OrnamentalTreeApplet()
	{
		_instance = this;
		visualizer = new OrnamentalTreeVisualizer(this);
	}
	
	public void setup()
	{	
		size(640, 480);
		background(255);
		
		visualizer.setup();
		MidiManagerIn mng = new MidiManagerIn();
		mng.addHanler(visualizer);
		//mng.showDevices();
		mng.startEmulation();
		//mng.startDevice(0);
		
	}

	public void draw(){
		visualizer.draw();
	}
}
