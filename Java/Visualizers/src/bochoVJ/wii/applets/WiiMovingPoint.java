package bochoVJ.wii.applets;


import bochoVJ.wii.IWiiHandler;
import bochoVJ.wii.WiiManager;
import processing.core.PApplet;

public class WiiMovingPoint extends PApplet {
	
	WiiManager wiimng;
	
	private final int xSize = 400;
	private final int ySize = 400;
	
	private float xspeed = 0, yspeed = 0;
	private float xpos = 200, ypos = 200;
	
	private float accFactor = 1;
	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", WiiMovingPoint.class.getName() });
	  }
	
	public void setup()
	{	
		wiimng = new WiiManager();
		wiimng.addHandler(new IWiiHandler() {
			
			@Override
			public void handleButton(WiiButton b) {
			}
			
			@Override
			public void handleAcc(Acceleration acc) {
				xspeed = (float)(acc.x * accFactor);
				yspeed = (float)(acc.y * accFactor);
			}
		});
		
		size(xSize, ySize); 
		background(0);
	}

	public void draw(){
		background(0);
		
		xpos += xspeed;
		if(xpos < 0)
			xpos = xSize;
		if(ypos > xSize)
			xpos =0;
		
		ypos += yspeed;
		if(ypos < 0)
			ypos = ySize;
		if(ypos > ySize)
			ypos =0;
		
		fill(250);
		stroke(250);
		ellipse(xpos, ypos, 5, 5);
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
