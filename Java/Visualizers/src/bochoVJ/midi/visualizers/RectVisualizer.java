package bochoVJ.midi.visualizers;
import processing.core.PApplet;
import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.applets.IMidiVisualizer;

public class RectVisualizer implements IMidiVisualizer {

    	PApplet app;
    	
	public RectVisualizer(PApplet applet)
	{
	    app = applet;
	    
		for(int i =0; i< 12; i++)
		{
			this.rects[i][0] = 0;
			this.rects[i][1] = 0;
		}
	}

	/**
	 * rects[i] i rectangle
	 * rects[i][0] intensity of the last note
	 * rects[i][1] current step
	 */
	int[][] rects = new int[12][2];
	int speed = 5;

	@Override
	public void handleNote(int channel, int note, int intensity) {
		int rectIndex = note%12;
		this.rects[rectIndex][0] = intensity; //set intensity
		this.rects[rectIndex][1] = 255; //reset step
	}

	public void draw()
	{
		for(int i =0; i< 12; i++)
		{
			if(rects[i][1] >0)
			{
				app.fill(rects[i][1]);
				app.noStroke();
				float x = (app.width/12)*i;
				float y = rects[i][0]*((float)app.height /(float)128);
				app.rect(x, y, 20, 20);

				rects[i][1] -= speed;
			}
		}
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleControlChange(int channel, int controlN, int value) {
	    
	}

}
