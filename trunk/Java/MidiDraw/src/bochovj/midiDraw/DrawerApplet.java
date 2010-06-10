/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.midiDraw;

import java.awt.Color;
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import bochovj.draw.IStrokeHandler;
import bochovj.draw.IStrokeInput;
import bochovj.draw.Point;
import bochovj.draw.Stroke;
import bochovj.video.VideoGrabber;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * The drawing program
 * @author bochovj
 *
 */
public class DrawerApplet extends PApplet implements IStrokeInput{

    private static final long serialVersionUID = -5024248105361950846L;
    
    private static DrawerApplet _instance;
    public static DrawerApplet getInstance()
    {
	return _instance;
    }

    public static final int xSize = 800;
    public static final int ySize = 600;

    private Color strokeColor = new Color(255,255,255);
    
    public Iterable<Stroke> strokes;
    public Point lastaddedpoint = new Point(0, 0, strokeColor, 0);
    
    private List<IStrokeHandler> strokeHandlers;
    @Override
    public void registerStrokeHandler(IStrokeHandler h) {
	if(h!= null)
	    if(!strokeHandlers.contains(h))
		strokeHandlers.add(h);
    }

    private float currentStrokeWidth = 1;

    List<FeatureExtractor> featureExtractors;

    public static VideoGrabber videograbber;
    PImage backgroundImg;
    PImage originalImg;

    public void handleBackGroundBlur(float value)
    {
	if(backgroundImg != null)
	    {
	    PImage newImage = null;
		try {  newImage = (PImage) originalImg.clone();} 
		catch (CloneNotSupportedException e) {}
		float blur = value * 4F;
		newImage.filter(BLUR, blur);
		backgroundImg = newImage;
	    } 
    }
    
    public void handleStrokeWidth(float value)
    {
	currentStrokeWidth = value * 4F;
    }
    
    public DrawerApplet() throws Exception
    {
	//Creates singleton instance
	_instance = this;

	strokeHandlers = new LinkedList<IStrokeHandler>();
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

	strokeJoin(ROUND);
	strokeCap(ROUND);

	//Capture point
	if(mousePressed)
	{
	    if((lastaddedpoint.x != mouseX) || ((lastaddedpoint.y != mouseY)))
		for(IStrokeHandler h: strokeHandlers)
		    h.handleNewPoint(new Point(mouseX, mouseY, strokeColor, currentStrokeWidth));
	}

	//Draw strokes
	if(strokes != null)
	    for(Stroke str : strokes)
	    {
		Iterator<Point> piter = str.getClonedIterator();
		if(str.getPointsNumber()>1)
		{
		    Point previousPoint = piter.next();
		    while(piter.hasNext())
		    {
			Point nextPoint = piter.next();
			stroke(nextPoint.color.getRed(), nextPoint.color.getGreen(), nextPoint.color.getBlue());
			strokeWeight(nextPoint.weight);
			line(previousPoint.x, previousPoint.y, nextPoint.x, nextPoint.y);
			previousPoint = nextPoint;
		    }
		}
	    }
    }

    /**
     * When pressed a new stroke begins
     */
    public void mousePressed()
    {
	for(IStrokeHandler h: strokeHandlers)
	    h.handleNewStroke();
    }

    /**
     * Used to interact with the user:
     * i: captures the image
     */
    public void keyPressed()
    {
	if(key == 'i')
	    printCamImg();
    }

    /**
     * Prints the captured image as background
     */
    private void printCamImg()
    {
	if(videograbber != null)
	{
	    Image img = videograbber.captureImage();
	    System.out.println("Capturing image");
	    originalImg = new PImage(img);
	    originalImg.filter(GRAY);
	    try 
	    {
		backgroundImg = (PImage) originalImg.clone();
	    } catch (CloneNotSupportedException e) {}
	}
    }

}
