/**
 * Common libraries
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.draw;


public interface IStrokeHandler {
    
    /**
     * Handles when a new point is detected
     * @param p the new point
     */
    void handleNewPoint(Point p);
    
    /**
     * Handles when a new stroke is begun
     */
    void handleNewStroke();

}
