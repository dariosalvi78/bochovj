/**
 * A visualizer that makes falling snow balls out of the notes
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.midi.visualizers;

import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;

import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.applets.IMidiVisualizer;

/**
 * A visualizer that makes falling snow balls out of the notes
 */
public class ShowerVisualizer implements IMidiVisualizer {

    PApplet app;

    Object mutex = new Object();

    /**
     * A falling ball
     */
    private class Drop
    {
	public int x;
	public int y;
	public int size;
	public int speed;

	public Drop(int x, int y, int size, int speed)
	{
	    this.x = x;
	    this.y = y;
	    this.size = size;
	    this.speed = speed;
	}
    }

    private List<Drop> drops;

    public ShowerVisualizer(PApplet applet)
    {
	app = applet;
	this.drops = new LinkedList<Drop>();
    }

    @Override
    public void handleNote(int channel, int note, int intensity) {
	int x = (app.width / 12) * (note%12) + app.round(((float)intensity/128)*(app.width / 12));
	int size = app.round(((float)intensity/128)*10);
	int speed = app.round(((float)intensity/128)*(6));
	synchronized (mutex) 
	{
	    this.drops.add(new Drop(x, 0, size, speed));	
	}
    }

    public void draw()
    {
	synchronized (mutex) {
	    for(int i=0; i< this.drops.size(); i++)
	    {
		Drop d = drops.get(i);
		if(d.y < app.height)
		{
		    d.y += d.speed+1;
		    app.fill(255);
		    app.stroke(255);
		    app.ellipse(d.x, d.y, d.size, d.size);
		}
		else
		    this.drops.remove(i);
	    }	
	}
    }

    @Override
    public void handleControlChange(int channel, int controlN, int value) {
	
    }
    
    @Override
    public void setup() {
	// TODO Auto-generated method stub

    }



}
