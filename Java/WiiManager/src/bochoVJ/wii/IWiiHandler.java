/**
 * 
 */
package bochoVJ.wii;

/**
 * @author Dario Salvi
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
