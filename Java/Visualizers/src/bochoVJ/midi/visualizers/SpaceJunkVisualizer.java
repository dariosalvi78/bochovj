package bochoVJ.midi.visualizers;

import processing.core.PApplet;
import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.applets.IMidiVisualizer;

/**
 * Space Junk  
 * by Ira Greenberg. 
 * Zoom suggestion 
 * by Danny Greenberg.
 * 
 * Rotating cubes in space using a custom Cube class. 
 * Color controlled by light sources. Move the mouse left
 * and right to zoom.
 */
public class SpaceJunkVisualizer implements IMidiVisualizer {

	
	PApplet app;

	// Used for oveall rotation
	float ang;

	// Cube count-lower/raise to test P3D/OPENGL performance
	int limit = 500;

	// Array for all cubes
	Cube[]cubes = new Cube[limit];
	
	public SpaceJunkVisualizer(PApplet applet)
	{
	    app = applet;
	}
	
	@Override
	public void draw() {
		 app.background(0); 
		 app.fill(200);

		  // Set up some different colored lights
		 app.pointLight(51, 102, 255, 65, 60, 100); 
		 app.pointLight(200, 40, 60, -65, -60, -150);

		  // Raise overall light in scene 
		 app.ambientLight(70, 70, 10); 

		  // Center geometry in display windwow.
		  // you can change 3rd argument ('0')
		  // to move block group closer(+)/further(-)
		 app.translate(app.width/2, app.height/2, (float) (-200 + app.mouseX * 0.65));

		  // Rotate around y and x axes
		 app.rotateY(app.radians(ang));
		 app.rotateX(app.radians(ang));

		  // Draw cubes
		  for (int i = 0; i < cubes.length; i++){
		    cubes[i].drawCube();
		  }
		  
		  // Used in rotate function calls above
		  ang++;
	}

	@Override
	public void setup() {
		
		app.noStroke();

		  // Instantiate cubes, passing in random vals for size and postion
		  for (int i = 0; i< cubes.length; i++){
		    cubes[i] = new Cube((int)(app.random(-10, 10)), (int)(app.random(-10, 10)), 
		    		(int)(app.random(-10, 10)), (int)(app.random(-140, 140)), (int)(app.random(-140, 140)), 
		    		(int)(app.random(-140, 140)));
		  }
	}
	
	
	class Cube {

		  // Properties
		  int w, h, d;
		  int shiftX, shiftY, shiftZ;

		  // Constructor
		  Cube(int w, int h, int d, int shiftX, int shiftY, int shiftZ){
		    this.w = w;
		    this.h = h;
		    this.d = d;
		    this.shiftX = shiftX;
		    this.shiftY = shiftY;
		    this.shiftZ = shiftZ;
		  }

		  // Main cube drawing method, which looks 
		  // more confusing than it really is. It's 
		  // just a bunch of rectangles drawn for 
		  // each cube face
		  void drawCube(){
			  app.beginShape(app.QUADS);
		    // Front face
		    app.vertex(-w/2 + shiftX, -h/2 + shiftY, -d/2 + shiftZ); 
		    app.vertex(w + shiftX, -h/2 + shiftY, -d/2 + shiftZ); 
		    app.vertex(w + shiftX, h + shiftY, -d/2 + shiftZ); 
		    app.vertex(-w/2 + shiftX, h + shiftY, -d/2 + shiftZ); 

		    // Back face
		    app.vertex(-w/2 + shiftX, -h/2 + shiftY, d + shiftZ); 
		    app.vertex(w + shiftX, -h/2 + shiftY, d + shiftZ); 
		    app.vertex(w + shiftX, h + shiftY, d + shiftZ); 
		    app.vertex(-w/2 + shiftX, h + shiftY, d + shiftZ);

		    // Left face
		    app.vertex(-w/2 + shiftX, -h/2 + shiftY, -d/2 + shiftZ); 
		    app.vertex(-w/2 + shiftX, -h/2 + shiftY, d + shiftZ); 
		    app.vertex(-w/2 + shiftX, h + shiftY, d + shiftZ); 
		    app.vertex(-w/2 + shiftX, h + shiftY, -d/2 + shiftZ); 

		    // Right face
		    app.vertex(w + shiftX, -h/2 + shiftY, -d/2 + shiftZ); 
		    app.vertex(w + shiftX, -h/2 + shiftY, d + shiftZ); 
		    app.vertex(w + shiftX, h + shiftY, d + shiftZ); 
		    app.vertex(w + shiftX, h + shiftY, -d/2 + shiftZ); 

		    // Top face
		    app.vertex(-w/2 + shiftX, -h/2 + shiftY, -d/2 + shiftZ); 
		    app.vertex(w + shiftX, -h/2 + shiftY, -d/2 + shiftZ); 
		    app.vertex(w + shiftX, -h/2 + shiftY, d + shiftZ); 
		    app.vertex(-w/2 + shiftX, -h/2 + shiftY, d + shiftZ); 

		    // Bottom face
		    app.vertex(-w/2 + shiftX, h + shiftY, -d/2 + shiftZ); 
		    app.vertex(w + shiftX, h + shiftY, -d/2 + shiftZ); 
		    app.vertex(w + shiftX, h + shiftY, d + shiftZ); 
		    app.vertex(-w/2 + shiftX, h + shiftY, d + shiftZ); 

		    app.endShape(); 

		    // Add some rotation to each box for pizazz.
		    app.rotateY(app.radians(1));
		    app.rotateX(app.radians(1));
		    app.rotateZ(app.radians(1));
		  }
		}


	@Override
	public void handleControlChange(int channel, int controlN, int value) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void handleNote(int channel, int note, int intensity) {
	    // TODO Auto-generated method stub
	    
	}

}



