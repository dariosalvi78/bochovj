/**
 * 
 */
package bochovj.midiDraw;

/**
 * A stroke input captures strokes and their points
 * 
 * @author bochoVJ
 *
 */
public interface IStrokeInput {

    /**
     * Used to register an handler to strokes events
     */
    void registerStrokeHandler(IStrokeHandler h);
    
}
