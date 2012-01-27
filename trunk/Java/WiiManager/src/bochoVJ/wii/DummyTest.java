/**
 * Wrapper for wiiRemoteJ
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.wii;


/**
 * A simple test for the WiiManager.
 * @author bochovj
 *
 */
public class DummyTest {

	/**
	 * A simple WiiMote handler, just prints the events on console
	 * @author bochovj
	 *
	 */
	public static class DummyWiiHandler implements IWiiHandler {

		public double max;

		@Override
		public void handleAcc(Acceleration acc) {
			//System.out.println("Acceleration: x "+acc.x+" y "+acc.y+" z "+acc.z);
			if(acc.x > max)
				max = acc.x;
			if(acc.y > max)
				max = acc.y;
			if(acc.z > max)
				max = acc.z;
		}

		@Override
		public void handleButton(WiiButton b) {
			System.out.println("Button "+b.toString());
		}
	}

	public static void main(String[] args) throws Exception
	{
		IWiiManager mng = new WiiuseJManager();
		DummyWiiHandler handler = new DummyWiiHandler();
		mng.addHandler(handler);

		mng.connect();

		Thread.sleep(60000);

		mng.disconnect();

		System.out.println("Max: "+handler.max);
	}

}


