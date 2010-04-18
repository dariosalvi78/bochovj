/**
 * 
 */
package bochoVJ.midi.visualizers;

import java.util.Iterator;
import java.util.LinkedList;

import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.MidiApplet;
import bochoVJ.midi.applets.IMidiVisualizer;

/**
 * Ornamental Tree by Algirdas Rascius (http://mydigiverse.com).
 */
public class OrnamentalTreeVisualizer implements IMidiVisualizer
{
	MidiApplet app;

	int SIZE = 25;
	int MARGIN = 25;
	float LEAF_SIZE = 0.4F;
	float SEED_SIZE = 0.3F;

	float animateProbability;
	int sizeX;
	int sizeY;
	int dirsX[][];
	int dirsY[][];
	boolean isBranch[][];
	LinkedList points1;
	LinkedList points2;
	Iterator pointIterator;

	public OrnamentalTreeVisualizer() 
	{
		app = MidiApplet.getInstance();
	}

	public void setup() {
		sizeX = (app.width - MARGIN) / SIZE;
		sizeY = (app.height - MARGIN) / SIZE;
		dirsX = new int[sizeX][sizeY];
		dirsY = new int[sizeX][sizeY];
		isBranch = new boolean[sizeX][sizeY];
		points1 = new LinkedList();
		points2 = new LinkedList();
		
		animateProbability = app.random(0.05F, 1);
		for (int x=0; x<sizeX; x++) {
			for (int y=0; y<sizeY; y++) {
				dirsX[x][y] = 0;
				dirsY[x][y] = 0;
				isBranch[x][y] = false;
			}
		}
		points1.clear();
		points2.clear();
		pointIterator = points1.iterator();
	}

	void drawArc(float x1, float y1, float xc, float yc, float x2, float y2) {
		app.beginShape();
		app.vertex(x1, y1);
		app.bezierVertex(
				(x1+xc)/2, (y1+yc)/2,
				(x2+xc)/2, (y2+yc)/2,
				x2, y2);
		app.endShape();
	}

	class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		boolean animate() {
			int r1 = (int)app.random(3);
			int r2 = (int)app.random(2) + 1;
			for (int i=0; i<3; i++) {
				if (r1 == 0) {
					if (canAnimateX()) {
						animateX();
						return true;
					}
				}
				if (r1 == 1) {
					if (canAnimateY()) {
						animateY();
						return true;
					}
				}
				if (r1 == 2) {
					if (canAnimateXY()) {
						animateXY();
						return true;
					}
				}
				r1 = (r1+r2)%3;
			}
			if (!isBranch[x][y]) {
				drawLeaf();
			}
			return false;
		}

		boolean canAnimateX() {
			int newX = x + dirsX[x][y];
			return newX>=0 && newX<sizeX && dirsX[newX][y]==0;
		}

		boolean canAnimateY() {
			int newY = y + dirsY[x][y];
			return newY>=0 && newY<sizeY && dirsY[x][newY]==0;
		}

		boolean canAnimateXY() {
			int newX = x + dirsX[x][y];
			int newY = y + dirsY[x][y];
			return newX>=0 && newX<sizeX && newY>=0 && newY<sizeY && dirsY[newX][newY]==0 && 
			(newX+dirsX[newX][y]!=x || y+dirsY[newX][y]!=newY) && (x+dirsX[x][newY]!=newX || newY+dirsY[x][newY]!=y); 
		}

		void animateX() {
			int newX = x + dirsX[x][y];
			dirsX[newX][y] = dirsX[x][y];
			dirsY[newX][y] = -dirsY[x][y];
			isBranch[x][y] = true;
			points2.add(new Point(newX, y));
			app.strokeWeight(1.5F/SIZE);
			drawArc(x, y, (x+newX)*0.5F, y+dirsY[x][y]*0.5F, newX, y);    
		}

		void animateY() {
			int newY = y + dirsY[x][y];
			dirsX[x][newY] = -dirsX[x][y];
			dirsY[x][newY] = dirsY[x][y];
			isBranch[x][y] = true;
			points2.add(new Point(x, newY));
			app.strokeWeight(1.5F/SIZE);
			drawArc(x, y, x+dirsX[x][y]*0.5F, (y+newY)*0.5F, x, newY);
		}

		void animateXY() {
			int newX = x + dirsX[x][y];
			int newY = y + dirsY[x][y];
			isBranch[x][y] = true;
			dirsX[newX][newY] = dirsX[x][y];
			dirsY[newX][newY] = dirsY[x][y];
			points2.add(new Point(newX, newY));
			app.strokeWeight(1.5F/SIZE);
			app.line(x, y, newX, newY);
		}

		void drawLeaf() {
			app.strokeWeight(1.0F/SIZE);
			app.beginShape();
			app.vertex(x, y);
			app.bezierVertex(
					x-dirsX[x][y]*LEAF_SIZE*2/8, y+dirsY[x][y]*LEAF_SIZE*2/8,
					x-dirsX[x][y]*LEAF_SIZE*3/8, y+dirsY[x][y]*LEAF_SIZE*4/8,
					x-dirsX[x][y]*LEAF_SIZE*1/8, y+dirsY[x][y]*LEAF_SIZE*6/8);          
			app.bezierVertex(
					x+dirsX[x][y]*LEAF_SIZE*1/8, y+dirsY[x][y]*LEAF_SIZE*8/8,
					x+dirsX[x][y]*LEAF_SIZE*6/8, y+dirsY[x][y]*LEAF_SIZE*6/8,
					x+dirsX[x][y]*LEAF_SIZE*8/8, y+dirsY[x][y]*LEAF_SIZE*8/8);  
			app.bezierVertex(
					x+dirsX[x][y]*LEAF_SIZE*6/8, y+dirsY[x][y]*LEAF_SIZE*6/8,
					x+dirsX[x][y]*LEAF_SIZE*8/8, y+dirsY[x][y]*LEAF_SIZE*1/8,
					x+dirsX[x][y]*LEAF_SIZE*6/8, y-dirsY[x][y]*LEAF_SIZE*1/8);      
			app.bezierVertex(
					x+dirsX[x][y]*LEAF_SIZE*4/8, y-dirsY[x][y]*LEAF_SIZE*3/8,
					x+dirsX[x][y]*LEAF_SIZE*2/8, y-dirsY[x][y]*LEAF_SIZE*2/8,
					x, y);
			app.endShape();
		}
	}



	public void draw()
	{
		app.smooth();
		app.stroke(0);
		app.strokeWeight(1.0F/SIZE);
		app.noFill();

		app.translate(MARGIN, MARGIN);
		app.scale(SIZE);

		for (int i=0; i<2/animateProbability; i++) {  
			if (pointIterator.hasNext()) {
				Point p = (Point)pointIterator.next();
				if (app.random(1) < animateProbability) {
					if (!p.animate()) {
						pointIterator.remove();
					}
				}
			} else {
				
				LinkedList tempPoints = points1;
				points1 = points2;
				points2 = tempPoints;
				//addRandomPoint();
				pointIterator = points1.iterator();
			}
		}
	}

	@Override
	public void handleNote(int channel, int note, int intensity) {
		int x = (int)app.random(sizeX-2) + 1;
		int y = (int)app.random(sizeY-2) + 1;
		if (dirsX[x][y] == 0) {
			float randX = note/127;
			float randY = intensity/127;
			dirsX[x][y] = app.random(randX)< 0.5 ? 1 : -1;
			dirsY[x][y] = app.random(randY)< 0.5 ? 1 : -1;
			isBranch[x][y] = true;
			points2.add(new Point(x, y));
			app.fill(0);
			app.ellipse(x, y, SEED_SIZE, SEED_SIZE);
			app.noFill();
		}
	}

	@Override
	public void handleControlChange(int channel, int controlN, int value) {
	    
	}

}
