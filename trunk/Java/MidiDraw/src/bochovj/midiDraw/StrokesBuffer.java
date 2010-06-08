/**
 * 
 */
package bochovj.midiDraw;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author bochovj
 *
 */
public class StrokesBuffer implements IStrokeHandler {

    ConcurrentLinkedQueue<Stroke> strokes;

    Stroke currentStroke;

    final int disappearRate = 200;


    public StrokesBuffer(boolean autoDisappear)
    {
	strokes = new ConcurrentLinkedQueue<Stroke>();

	currentStroke = null;

	//Start disappearing thread
	if(autoDisappear)
	    new Thread(new Runnable() {
		@Override
		public void run() {
		    try 
		    {
			while(true)
			{
			    Stroke str = strokes.peek();
			    if(str != null)
				if(str.removeLastPoint() == null)
				    strokes.remove(str);

			    Thread.sleep(disappearRate);
			} 
		    } 
		    catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }, "Disappearing points thread").start();
    }

    @Override
    public void handleNewPoint(Point p) {

	//Get point
	if(currentStroke != null)
	{
	    Point lastpoint = currentStroke.getLastPoint();
	    //Add only if really new
	    if((lastpoint == null) || ((lastpoint.x != p.x)||(lastpoint.y!= p.y)))
		currentStroke.insertPoint(p);
	}

    }

    @Override
    public void handleNewStroke() {
	//Creating a new stroke
	currentStroke = new Stroke();
	strokes.offer(currentStroke);
    }

    
    public Iterable<Stroke> getStrokes()
    {
	return strokes;
    }

}
