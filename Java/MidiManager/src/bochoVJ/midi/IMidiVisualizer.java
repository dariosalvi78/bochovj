/**
 * Midi abstraction, uses javax.sound.midi
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
public interface IMidiVisualizer {
	
	public void visualizeNote(int note, int intensity);

	public void setup();
	
	public void draw();
}
