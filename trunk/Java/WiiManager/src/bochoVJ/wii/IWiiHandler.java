/**
 * Wrapper for wiiRemoteJ
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.wii;

/**
 * @author bochovj
 *
 */
public interface IWiiHandler {
	
	public class Acceleration
	{
		public double x, y, z;
		
	}
	
	public enum WiiButton { A, B, LEFT, RIGHT, UP, DOWN, MINUS, PLUS, HOME, ONE, TWO};
	
	public void handleAcc(Acceleration acc);

	public void handleButton(WiiButton b);
	
}
