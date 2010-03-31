package bochoVJ.wii;

public class DummyTest {
	
	public static void main(String[] args) throws Exception
	{
		WiiManager mng = new WiiManager();
		DummyWiiHandler handler = new DummyWiiHandler();
		mng.addHandler(handler);
		
		mng.connect();
		
		Thread.sleep(60000);
		
		mng.disconnect();
	}

}
