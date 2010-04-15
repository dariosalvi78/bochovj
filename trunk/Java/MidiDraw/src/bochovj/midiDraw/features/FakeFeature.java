/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.midiDraw.features;

import java.util.Queue;

import bochovj.midiDraw.FeatureExtractor;
import bochovj.midiDraw.Stroke;

/**
 * @author bochovj
 *
 */
public class FakeFeature extends FeatureExtractor {

    int value = 0;
    
    /**
     * @param strokes
     * @param channelNumber
     * @param controlNumber
     */
    public FakeFeature(Queue<Stroke> strokes, int channelNumber, int controlNumber) {
	super(strokes, channelNumber, controlNumber);
    }

    /* (non-Javadoc)
     * @see bochovj.FeatureExtractor#extractFeature(java.util.Queue)
     */
    @Override
    protected int extractFeature(Queue<Stroke> strokes) {
	
	value++;
	
	if(value == 128)
	    value = 0;
	
	return value;
    }

}
