package bochoVJ.midi.applets;

import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import bochoVJ.midi.MidiManagerIn;
import bochoVJ.midi.visualizers.LinesVisualizer;
import bochoVJ.midi.visualizers.SpaceJunkVisualizer;

public class GeneralApplet extends PApplet {

    private List<IMidiVisualizer> visualizers;

    public static void main(String args[]) {
	PApplet.main(new String[] { "--present", GeneralApplet.class.getName() });
    }


    public GeneralApplet()
    {
	visualizers = new LinkedList<IMidiVisualizer>();
	visualizers.add(new LinesVisualizer(this));
	visualizers.add(new SpaceJunkVisualizer(this));
    }

    public void setup()
    {	
	size(1024, 768); 
	background(0); 

	MidiManagerIn mng = new MidiManagerIn();
	for(IMidiVisualizer vis : this.visualizers)
	    mng.addHanler(vis);

	for(IMidiVisualizer vis : this.visualizers)
	    vis.setup();

	//mng.showDevices();
	mng.startEmulation();
	//mng.startDevice(0);
    }

    public void draw(){
	background(0); 

	for(IMidiVisualizer vis : this.visualizers)
	    vis.draw();
    }
}
