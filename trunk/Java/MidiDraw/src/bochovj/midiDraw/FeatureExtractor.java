/**
 * Midi Drawer, a program that translates drawings to midi messages
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.midiDraw;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Abstract class for feature extractor
 * @author bochovj
 *
 */
public abstract class FeatureExtractor implements IStrokeHandler {
    
    protected StrokesBuffer strokes;
    
    private List<IFeatureHandler> handlers;
    public void registerFeatureHandler(IFeatureHandler h)
    {
	if(!handlers.contains(h))
	    handlers.add(h);
    }
    
    public void unregisterFeatureHandler(IFeatureHandler h)
    {
	if(handlers.contains(h))
	    handlers.remove(h);
    }
    
    private int lastValue;
    

    public FeatureExtractor(StrokesBuffer strokesBuff)
    {
	this.strokes = strokesBuff;
	
	handlers = new LinkedList<IFeatureHandler>();
    }
    
    /**
     * Extracts a feature
     * @return a value between 0 and 127
     */
    protected abstract int extractFeature();
    
    private void sendFeature()
    {
	int currentValue = extractFeature();
	if(lastValue != currentValue)
	{
	    lastValue = currentValue;
	    for(IFeatureHandler h: handlers)
		h.handleFeature(this, currentValue);
	}
    }
    
    @Override
    public void handleNewPoint(Point p) {
	addNewPoint(p);
	sendFeature();
    }

    @Override
    public void handleNewStroke() {
	addNewStroke();
	sendFeature();
    }
    
    protected abstract void addNewPoint(Point p);

    protected abstract void addNewStroke();
}
