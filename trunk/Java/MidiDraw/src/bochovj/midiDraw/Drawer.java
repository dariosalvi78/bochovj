package bochovj.midiDraw;
import bochoVJ.midi.MidiManagerIn;
import bochovj.midiDraw.features.Emptiness;
import processing.core.PApplet;

/**
 * This class was needed to start the video capturing, 
 * which on some OSs is not allowed in an Applet (e.g. Windows Vista).
 * As it was there, I've isolated the Drawer Applet functionalities 
 * to only graphical input/ouput, and this class as the most external container
 * 
 * @author bochovj
 *
 */
public class Drawer {
    
    public static void main(String args[]) throws Exception {
	
	//Starts the applet in full screen
	PApplet.main(new String[] { "--present", "bochovj.midiDraw.DrawerApplet" });
	
	//Video capturing must be initialized outside the Applet
	//Or it won't ever get permissions in Windows Vista
	DrawerApplet.videograbber = new VideoGrabber();
	try{
	    DrawerApplet.videograbber.startCapture();
	}
	catch(Exception ex)
	{
	    System.out.println("Could not open capture device, yuou won't be able to use it");
	}
	
	//Initialize Midi Input and Output 
	MidiOut midiout = new MidiOut();
	int outDev = midiout.promptUserSelectDevice();
	midiout.startDevice(outDev);
	
	MidiIn midiin = new MidiIn();
	int inDev = midiin.promptUserSelectDevice();
	midiin.startDevice(inDev);
	midiin.registerDrawerApplet(DrawerApplet.getInstance(), 0, 74, 0, 76);
	
	//Initialize StrokesBuffer
	StrokesBuffer strokesbuffer = new StrokesBuffer(false);
	DrawerApplet.getInstance().strokes = strokesbuffer.getStrokes();
	DrawerApplet.getInstance().registerStrokeHandler(strokesbuffer);
	
	//Register features
	Emptiness emptiness = new Emptiness(strokesbuffer);
	emptiness.registerFeatureHandler(midiout); //Route feature to midi Out
	midiout.setMidiNumbers(emptiness, 1, 16);
	
	//Register features
	DrawerApplet.getInstance().registerStrokeHandler(emptiness);
    }

}
