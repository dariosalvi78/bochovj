package bochoVJ.midi;
import java.util.LinkedList;
import java.util.List;

import bochoVJ.midi.IMidiHandler;

import processing.core.PApplet;

public abstract class MidiApplet extends PApplet {
	
	protected static MidiApplet _instance;
	
	public static MidiApplet getInstance()
	{
		return _instance;
	}
}
