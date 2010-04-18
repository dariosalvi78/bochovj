package bochoVJ.wii.applets;


import bochoVJ.wii.IWiiHandler;
import bochoVJ.wii.WiiManager;
import processing.core.PApplet;
import processing.core.PFont;

public class WiiTester extends PApplet {
	
	WiiManager wiimng;
	
	private final int xSize = 400;
	private final int ySize = 400;
	
	private int[] accX = new int[xSize];
	private int accXPointer = 0;
	
	private int[] accY = new int[xSize];
	private int accYPointer = 0;
	
	private int[] accZ = new int[xSize];
	private int accZPointer = 0;
	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", WiiTester.class.getName() });
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
				accX[accXPointer] = (int)(acc.x * 10);
				accXPointer ++;
				if(accXPointer >= xSize)
					accXPointer = 0;
				
				accY[accYPointer] = (int)(acc.y * 10);
				accYPointer ++;
				if(accYPointer >= xSize)
					accYPointer = 0;
				
				accZ[accZPointer] = (int)(acc.z * 10);
				accZPointer ++;
				if(accZPointer >= xSize)
					accZPointer = 0;
			}
		});
		
		size(xSize, ySize); 
		background(0);
	}

	public void draw(){
		background(0);
		
		fill(0, 102, 153);
		PFont font;
		font = loadFont("CourierNew36.vlw"); 
		textFont(font, 15); 
		text("Press c to connect, d to disconnect", 10, 20);
		
		stroke(250);
		for (int i=0; i< xSize; i++)
		{
			point(i, accX[i]+200);
		}
		
		stroke(250, 0, 0);
		for (int i=0; i< xSize; i++)
		{
			point(i, accY[i]+200);
		}
		
		stroke(0, 250, 0);
		for (int i=0; i< xSize; i++)
		{
			point(i, accZ[i]+200);
		}
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
		}
}
