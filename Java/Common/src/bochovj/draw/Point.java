/**
 * Common: general utilities
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.draw;

import java.awt.Color;

/**
 * A point is described by its position, its weight and its color
 * 
 * 
 * @author bochovj
 *
 */
public class Point {
    
    public float x,y;
    
    public Color color;
    
    public float weight;
    
    public Point(float x, float y, Color color, float weight)
    {
	this.x = x;
	this.y = y;
	this.color = color;
	this.weight = weight;
    }
}
