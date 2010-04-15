/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.midiDraw;

/**
 * A simple description of a point
 * @author bochovj
 *
 */
public class Point {
    
    public Point(float x, float y)
    {
	this.x = x;
	this.y = y;
    }

    public float x,y;
    
}
