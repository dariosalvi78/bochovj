package bochoVJ.midi.applets;
import java.util.LinkedList;

import processing.core.PApplet;

import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.MidiApplet;
import bochoVJ.midi.MidiManagerIn;
import bochoVJ.midi.visualizers.RectVisualizer;

public class RectsApplet extends MidiApplet{

    public static void main(String args[]) {
	PApplet.main(new String[] { "--present", RectsApplet.class.getName() });
    }

    private IMidiVisualizer visualizer;

    public RectsApplet()
    {
	_instance = this;
	visualizer = new RectVisualizer(this);
    }


    public void setup()
    {			
	MidiManagerIn mng = new MidiManagerIn();
	mng.addHanler(visualizer);
	//mng.startDevice(0);
	mng.startEmulation();
	size(640, 480);
	background(0);
    }

    public void draw(){
	background(0);
	visualizer.draw();
    }

}
