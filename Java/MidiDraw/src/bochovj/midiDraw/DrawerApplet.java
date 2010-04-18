/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.midiDraw;

import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.MidiManagerIn;
import bochoVJ.midi.MidiManagerOut;
import bochovj.midiDraw.features.Emptiness;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * The drawing program
 * @author bochovj
 *
 */
public class DrawerApplet extends PApplet{

    public static final int xSize = 800;
    public static final int ySize = 600;

    final int disappearRate = 200;
    final int featuresRate = 500;

    ConcurrentLinkedQueue<Stroke> strokes;

    Stroke currentStroke;
    
    private float currentStrokeWidth = 1;

    List<FeatureExtractor> featureExtractors;

    MidiManagerOut midiout;
    MidiManagerIn in;

    public static VideoGrabber videograbber;
    PImage backgroundImg;
    PImage originalImg;

    private int blurControlN = 71;
    private int strokeWidthControlN = 74;
    
    public DrawerApplet() throws Exception
    {
	strokes = new ConcurrentLinkedQueue<Stroke>();
	featureExtractors = new LinkedList<FeatureExtractor>();
	featureExtractors.add(new Emptiness(strokes, 1, 16)); //Emptiness on control 16

	midiout = new MidiManagerOut();
	int outDev = midiout.promptUserSelectDevice();
	midiout.startDevice(outDev);

	in = new MidiManagerIn();
	int inDev = in.promptUserSelectDevice();
	in.startDevice(inDev);


	in.addHanler(new IMidiHandler() {

	    @Override
	    public void handleNote(int channel, int note, int intensity) {

	    }

	    @Override
	    public void handleControlChange(int channel, int controlN, int value) {
		PImage newImage = null;
		try {  newImage = (PImage) originalImg.clone();} 
		catch (CloneNotSupportedException e) {}
		if(controlN == blurControlN)
		{
		    // Change image settings
		    if(backgroundImg != null)
		    {
			float blur = (value / 127F) * 4F;
			newImage.filter(BLUR, blur);
			backgroundImg = newImage;
		    } 
		}
		else if(controlN == strokeWidthControlN)
		{
		    // Change stroke Width
		    if(backgroundImg != null)
		    {
			currentStrokeWidth = (value / 127F) * 4F;
		    } 
		}
		
	    }
	});

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
				midiout.sendControlChange(fe.getChannelNumber(), fe.getControlNumber(), feature);
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

	//Draw background Image
	if(backgroundImg != null)
	    image(backgroundImg, 0, 0, xSize, ySize);

	stroke(255);
	strokeJoin(ROUND);
	strokeCap(ROUND);

	//Get point
	if(currentStroke != null)
	{
	    currentStroke.offer(new Point(mouseX, mouseY, currentStrokeWidth));
	}


	//Draw strokes
	for(Stroke str : strokes)
	{
	    Iterator<Point> piter = str.iterator();
	    if(str.size()>1)
	    {
		Point previousPoint = piter.next();
		while(piter.hasNext())
		{
		    Point nextPoint = piter.next();
		    strokeWeight(nextPoint.weight);
		    line(previousPoint.x, previousPoint.y, nextPoint.x, nextPoint.y);
		    previousPoint = nextPoint;
		}
	    }
	}


    }

    public void mousePressed()
    {
	//Creating a new stroke
	currentStroke = new Stroke();
	strokes.offer(currentStroke);
    }

    public void mouseReleased()
    {
	//Not creating strokes any more
	currentStroke = null;
    }

    public void keyPressed()
    {
	if(key == 'i')
	    printCamImg();
    }

    private void printCamImg()
    {
	Image img = videograbber.captureImage();
	System.out.println("Capturing image");
	originalImg = new PImage(img);
	originalImg.filter(GRAY);
	try 
	{
	    backgroundImg = (PImage) originalImg.clone();
	} catch (CloneNotSupportedException e) {
	}
    }

}
