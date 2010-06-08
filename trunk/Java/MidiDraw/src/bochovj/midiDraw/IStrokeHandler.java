package bochovj.midiDraw;

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
