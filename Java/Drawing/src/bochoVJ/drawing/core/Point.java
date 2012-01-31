/**
 * drawing.core contains basic concepts about drawing like points, lines, strokes, etc..
 * 
 */
package bochoVJ.drawing.core;

import java.awt.Color;

/**
 * A drawn point, with color and size.
 * 
 * @author bochoVJ
 *
 */
public class Point {

	private float x;
	private float y;
	private Color color;
	private float size;
	
	
	public Point(float x, float y, Color color, float size) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.size = size;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	
	
	
}
