/**
 * Visualizers for WiiManager
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.wii.applets;


import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import bochoVJ.wii.IWiiHandler;
import bochoVJ.wii.IWiiManager;
import bochoVJ.wii.WiiRemoteJManager;
import processing.core.PApplet;

/**
 * A drawer for the WiiMote
 * @author bochovj
 *
 */
public class WiiMovingPoint extends PApplet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3113723236065865739L;

	IWiiManager wiimng;

    private final int xSize = 600;
    private final int ySize = 600;

    private float xspeed = 0, yspeed = 0;
    private float xpos = xSize/2, ypos = ySize/2;

    private float size =1;
    private final float maxSize = 10;
    
    private Color currentColor;

    private float accFactor = 2;

    List<Point> points = new LinkedList<Point>();

    public static void main(String args[]) {
	PApplet.main(new String[] { "--present", WiiMovingPoint.class.getName() });
    }

    public void setup()
    {	
	wiimng = new WiiRemoteJManager();
	wiimng.addHandler(new IWiiHandler() {

	    @Override
	    public void handleButton(WiiButton bt) {
	    }

	    @Override
	    public void handleAcc(Acceleration acc) {
		xspeed = (float)(acc.x * accFactor);
		yspeed = (float)(acc.y * accFactor);
		size = abs(
			(float)map(acc.z, -5.5, 5.5, 1, maxSize));
		
		currentColor = new Color(
			(float)map(acc.x, -5.5, 5.5, 0, 1), 
			(float)map(acc.y, -5.5, 5.5, 0, 1), 
			(float)map(acc.z, -5.5, 5.5, 0, 1));
	    }
	});

	size(xSize, ySize); 
	background(0);
	currentColor = new Color(250,250,250);
    }

    public void draw(){
	background(0);
	strokeJoin(ROUND);
	strokeCap(ROUND);

	xpos += xspeed;
	if(xpos < 0)
	    xpos = 0;
	if(xpos > xSize)
	    xpos = xSize;

	ypos += yspeed;
	if(ypos < 0)
	    ypos = 0;
	if(ypos > ySize)
	    ypos = ySize;

	if(points.size() ==0)
	    points.add(new Point(xpos, ypos, currentColor, abs( xspeed) + abs(yspeed)));
	else
	{
	    Point p = points.get(0);
	    if(!((p.getX() == xpos) || (p.getY() == ypos)))
		points.add(new Point(xpos, ypos, currentColor, size));	
	}
	
	points.add(new Point(xpos, ypos, currentColor, abs( xspeed) + abs(yspeed)));
	
	smooth();
	if(points.size()>1)
	{
	    Iterator<Point> piter = points.iterator();
	    Point previousPoint = piter.next();
	    while(piter.hasNext())
	    {
		Point nextPoint = piter.next();
		strokeWeight(nextPoint.getSize());
		stroke(nextPoint.getColor().getRed(),
				nextPoint.getColor().getGreen(),
				nextPoint.getColor().getBlue(),100);
		
		line(previousPoint.getX(), previousPoint.getY(), nextPoint.getX(), nextPoint.getY());
		previousPoint = nextPoint;
	    }
	}
	noSmooth();

    }

    public void keyTyped() {
	if(key=='c')
	    try {
		wiimng.connect();
	    } catch (Exception e) {
		e.printStackTrace();
	    } 

	    if(key=='d')
		wiimng.disconnect();

	    if(key =='+')
		accFactor += 0.5;

	    if(key =='-')
		accFactor -= 0.5;
    }
}
