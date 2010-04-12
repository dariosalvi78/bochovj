/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.features;

import java.util.Queue;

import bochovj.FeatureExtractor;
import bochovj.Stroke;

/**
 * A simple feature that gives the emptiness of the drawing
 * @author bochovj
 *
 */
public class Emptiness extends FeatureExtractor {

    private final float totalPixels = 1000;

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
	    totalFulls+= str.size();
	
	if(totalFulls > totalPixels)
	    totalFulls = totalPixels;
	
	float fullness = (totalFulls / totalPixels) * 127;
	int emptiness = (int) (127 - fullness);
	return emptiness;
    }

}
