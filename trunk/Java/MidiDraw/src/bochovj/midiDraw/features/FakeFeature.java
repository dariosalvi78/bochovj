/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.midiDraw.features;

import bochovj.midiDraw.FeatureExtractor;
import bochovj.draw.Point;
import bochovj.draw.StrokesBuffer;

/**
 * A test feature
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
    public FakeFeature(StrokesBuffer buffer) {
	super(buffer);
    }

    /* (non-Javadoc)
     * @see bochovj.FeatureExtractor#extractFeature(java.util.Queue)
     */
    @Override
    protected int extractFeature() {
	
	value++;
	
	if(value == 128)
	    value = 0;
	
	return value;
    }

    @Override
    protected void addNewPoint(Point p) {
	// Nothing to do
    }

    @Override
    protected void addNewStroke() {
	// Nothing to do
    }

}
