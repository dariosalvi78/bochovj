/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.midiDraw.features;

import java.util.Queue;

import bochovj.midiDraw.DrawerApplet;
import bochovj.midiDraw.FeatureExtractor;
import bochovj.midiDraw.Stroke;

/**
 * A simple feature that gives the emptiness of the drawing
 * @author bochovj
 *
 */
public class Emptiness extends FeatureExtractor {

    private double factor = 50;
    
    public Emptiness(Queue<Stroke> strokes, int channelNumber, int controlNumber) {
	super(strokes, channelNumber, controlNumber);
    }

    /* (non-Javadoc)
     * @see bochovj.IFeatureExtractor#extractFeature(java.util.Queue)
     */
    @Override
    public int extractFeature(Queue<Stroke> strokes) {
	float totalFulls = 0;
	for(Stroke str: strokes)
	    totalFulls+= str.getPointsNumber();
	
	//Use A*ln(1/(F* x+1))+127, which is 127 when x=0, 0, when x=M (need to compute A)
	//F is an adjusting factor, that needs to be fine tuned
	
	int M = DrawerApplet.xSize * DrawerApplet.ySize;
	double A = -(127 / Math.log(1/((factor*M)+1)));
	
	double emptiness =( A*Math.log(1/((factor*totalFulls)+1)) )+127;
	System.out.println("Emptiness: "+emptiness);
	return (int)emptiness;
    }

}
