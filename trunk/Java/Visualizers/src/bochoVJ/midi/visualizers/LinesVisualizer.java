package bochoVJ.midi.visualizers;


import processing.core.PApplet;

import bochoVJ.midi.applets.IMidiVisualizer;

public class LinesVisualizer implements IMidiVisualizer {

	int[] lines = new int[12];

	PApplet app;

	Object mutex = new Object();

	private int steps = 30;
	private int maxweight = 10;
	
	public LinesVisualizer(PApplet applet)
	{
		app = applet;
	}

	@Override
	public void draw() {
		for(int i=0; i<12; i++)
		{
			int x = (app.width / 12) * i + app.round((app.width / 24)) + vibrate(lines[i]);
			if(lines[i] >0)
				lines[i]--;
			int weight =app.round((((float)lines[i]/steps)* maxweight));
			app.strokeWeight(weight);
			app.stroke(colorize(lines[i])[0],colorize(lines[i])[1],colorize(lines[i])[2]);
			app.line(x, 0, x, app.height);
		}
	}

	@Override
	public void handleNote(int channel, int note, int intensity) {

		lines[note%12] = app.round(((float)intensity/127) * steps);
	}
	
	@Override
	public void handleControlChange(int channel, int controlN, int value) {
	    
	}

	private int vibrate(int step)
	{
		return step%4;
	}

	private int[] colorize(int step)
	{
		return new int[]{app.round(((float)step/steps) * 255),0,0};
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}
}
