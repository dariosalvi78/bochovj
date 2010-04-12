/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sound.midi.MidiUnavailableException;

import bochoVJ.midi.MidiManagerOut;
import bochovj.features.Emptiness;
import bochovj.features.FakeFeature;

import processing.core.PApplet;

/**
 * The drawing program
 * @author bochovj
 *
 */
public class Drawer extends PApplet{

    public static final int xSize = 800;
    public static final int ySize = 600;

    final int disappearRate = 200;
    final int featuresRate = 500;

    ConcurrentLinkedQueue<Stroke> strokes;

    Stroke currentStroke;
    
    List<FeatureExtractor> featureExtractors;
    
    MidiManagerOut out;

    public Drawer() throws MidiUnavailableException
    {
	strokes = new ConcurrentLinkedQueue<Stroke>();
	featureExtractors = new LinkedList<FeatureExtractor>();
	featureExtractors.add(new Emptiness(strokes, 1, 16)); //Emptiness on control 16
	
	out = new MidiManagerOut();
	out.startDevice(11);
	
	//Start features thread
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		try 
		{
		    while(true)
		    {
			for(FeatureExtractor fe : featureExtractors)
			{
			    int feature = fe.getFeature();
			    //Output to midi
			    if(fe.hasValueChanged())
			    {
				out.sendControlChange(fe.getChannelNumber(), fe.getControlNumber(), feature);
				System.out.println("Feature: "+feature);
			    }
			}
			    
			Thread.sleep(disappearRate);
		    } 
		} 
		catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}).start();
	
	//Start disappearing thread
	new Thread(new Runnable() {
	    @Override
	    public void run() {
		try 
		{
		    while(true)
		    {
			Stroke str = strokes.peek();
			if(str != null)
			    if(str.poll() == null)
				strokes.remove(str);

			Thread.sleep(disappearRate);
		    } 
		} 
		catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}).start();
    }

    public void setup()
    {	
	size(xSize, ySize);
	background(0);
    }

    public void draw()
    {
	background(0);
	stroke(255);

	//Get point
	if(currentStroke != null)
	{
	    currentStroke.offer(new Point(mouseX, mouseY));
	}

	//Draw strokes
	for(Stroke str : strokes)
	{
	    for(Point p: str)
	    {
		ellipse(p.x, p.y, 2,2);
	    }
	}
    }

    public void mousePressed()
    {
	currentStroke = new Stroke();
	strokes.offer(currentStroke);
    }

    public void mouseReleased()
    {
	currentStroke = null;
    }

}
