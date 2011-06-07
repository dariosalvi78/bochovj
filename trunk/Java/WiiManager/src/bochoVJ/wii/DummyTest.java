/**
 * Wrapper for wiiRemoteJ
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.wii;

/**
 * A simple test for the WiiManager
 * @author bochovj
 *
 */
public class DummyTest {

    public static void main(String[] args) throws Exception
    {
	WiiManager mng = new WiiManager();
	DummyWiiHandler handler = new DummyWiiHandler();
	mng.addHandler(handler);

	mng.connect();

	Thread.sleep(60000);

	mng.disconnect();
	
	System.out.println("Max: "+handler.max);
    }

}
