/**
 * Common libraries
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.draw;

import java.util.Iterator;
import java.util.LinkedList;


/**
 * @author bochovj
 *
 */
public class Stroke {
    
    private LinkedList<Point> points = new LinkedList<Point>();
    
    public void insertPoint(Point p)
    {
	synchronized(this)
	{
	    points.offer(p);
	}
    }
    
    public Point getFirstPoint()
    {
	synchronized(this)
	{
	    return points.peekFirst();
	}
    }
    
    public Point getLastPoint()
    {
	synchronized(this)
	{
	    return points.peekLast();
	}
    }
    
    public Point removeLastPoint()
    {
	synchronized(this)
	{
	    return points.pollLast();
	}
    }
    
    public int getPointsNumber()
    {
	synchronized(this)
	{
	    return points.size();
	}
    }
    
    public Iterator<Point> getClonedIterator()
    {
	synchronized(this)
	{
	    return ((LinkedList<Point>) points.clone()).iterator();
	}
    }
}
