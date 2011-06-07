/**
 * Common libraries
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.draw;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import processing.core.PApplet;

/**
 * The drawing program
 * @author bochovj
 *
 */
public abstract class ADrawerApplet extends PApplet implements IStrokeInput{

    private static final long serialVersionUID = -5024248105361950846L;
    

    public static final int xSize = 800;
    public static final int ySize = 600;

    private float currentStrokeWidth = 1;
    private Color currentStrokeColor = new Color(255,255,255);
    
    public Iterable<Stroke> strokes;
    public Point lastaddedpoint = new Point(0, 0, currentStrokeColor, 0);
    
    private List<IStrokeHandler> strokeHandlers;
    @Override
    public void registerStrokeHandler(IStrokeHandler h) {
	if(h!= null)
	    if(!strokeHandlers.contains(h))
		strokeHandlers.add(h);
    }
    
    public void handleStrokeWidth(float value)
    {
	currentStrokeWidth = value * 4F;
    }
    
    public ADrawerApplet()
    {
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

	preDrawLine();
	
	//Capture point
	if(mousePressed)
	{
	    if((lastaddedpoint.x != mouseX) || ((lastaddedpoint.y != mouseY)))
		for(IStrokeHandler h: strokeHandlers)
		    h.handleNewPoint(new Point(mouseX, mouseY, currentStrokeColor, currentStrokeWidth));
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
			drawLine(nextPoint);
			line(previousPoint.x, previousPoint.y, nextPoint.x, nextPoint.y);
			previousPoint = nextPoint;
		    }
		}
	    }
    }
    
    protected abstract void preDrawLine();
    
    protected abstract void drawLine(Point p);
    

    /**
     * When pressed a new stroke begins
     */
    public void mousePressed()
    {
	for(IStrokeHandler h: strokeHandlers)
	    h.handleNewStroke();
    }

    

}
