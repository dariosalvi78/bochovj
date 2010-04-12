/**
 * Wrapper for wiiRemoteJ
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.wii;

/**
 * A handler for WiiMote events
 * @author bochovj
 *
 */
public interface IWiiHandler {

    /**
     * A simple acceleration object
     * 
     * @author bochovj
     */
    public class Acceleration
    {
	public double x, y, z;
    }

    /**
     * WiiMote's buttons
     */
    public enum WiiButton { A, B, LEFT, RIGHT, UP, DOWN, MINUS, PLUS, HOME, ONE, TWO };

    /**
     * Handles the acceleration received
     */
    public void handleAcc(Acceleration acc);

    /**
     * Handles the button pressed event
     */
    public void handleButton(WiiButton b);

}
