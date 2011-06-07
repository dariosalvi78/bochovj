/**
 * Common libraries
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochovj.draw;


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
