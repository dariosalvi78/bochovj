/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.midiDraw;

import java.util.Queue;

/**
 * Abstact class for feature extractor
 * @author bochovj
 *
 */
public abstract class FeatureExtractor {
    
    protected Queue<Stroke> strokes;
    
    private int lastValue;
    
    private boolean valueChanged = true;
    
    private int channelNumber, controlNumber;
    
    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    public int getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(int controlNumber) {
        this.controlNumber = controlNumber;
    }

    public FeatureExtractor(Queue<Stroke> strokes, int channelNumber, int controlNumber)
    {
	this.strokes = strokes;
	this.channelNumber = channelNumber;
	this.controlNumber = controlNumber;
    }
    
    public boolean hasValueChanged()
    {
	return valueChanged;
    }
    
    public int getFeature()
    {
	int currentValue = extractFeature(strokes);
	if(lastValue != currentValue)
	{
	    lastValue = currentValue;
	    valueChanged = true;
	}
	else
	    valueChanged = false;
	 
	return currentValue;
    }
    
    /**
     * Extracts a feature
     * @return a value between 0 and 127
     */
    protected abstract int extractFeature(Queue<Stroke> strokes);

}
