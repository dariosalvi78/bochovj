/**
 * Wrapper for javax.sound.midi
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.midi;

/**
 * 
 * @author bochovj
 *
 */
public interface IMidiHandler {
	
	public void handleNote(int channel, int note, int intensity);
	
	public void handleControlChange(int channel, int controlN, int value);

}
