package bochoVJ.midi.applets;


import processing.core.PApplet;
import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.MidiApplet;
import bochoVJ.midi.MidiManagerIn;
import bochoVJ.midi.visualizers.NetVisualizer;

public class NetApplet extends MidiApplet {

	private IMidiVisualizer visualizer;
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", NetApplet.class.getName() });
	  }
	
	
	public NetApplet()
	{
		_instance = this;
		visualizer = new NetVisualizer(this);
	}
	
	public void setup()
	{	
		size(800, 600);
		background(0);
		MidiManagerIn mng = new MidiManagerIn();
		mng.addHanler(visualizer);
		//mng.showDevices();
		mng.startEmulation();
		//mng.startDevice(0);
	}

	public void draw(){
		background(0);
		visualizer.draw();
	}
	
}
