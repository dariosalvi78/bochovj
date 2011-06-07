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

import bochovj.draw.ADrawerApplet;
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
public class DrawerApplet extends ADrawerApplet{

    private static DrawerApplet _instance;
    public static DrawerApplet getInstance()
    {
	if(_instance != null)
	    _instance = new DrawerApplet();
	
	return _instance;
    }
    
    @Override
    protected void drawLine(Point p) {
	stroke(p.color.getRed(), p.color.getGreen(), p.color.getBlue());
	strokeWeight(p.weight);
    }


    @Override
    protected void preDrawLine() {
	//Draw background Image
	if(backgroundImg != null)
	    image(backgroundImg, 0, 0, xSize, ySize);

	strokeJoin(ROUND);
	strokeCap(ROUND);
	
    }
    
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
