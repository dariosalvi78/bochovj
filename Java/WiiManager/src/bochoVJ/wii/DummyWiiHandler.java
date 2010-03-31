package bochoVJ.wii;

public class DummyWiiHandler implements IWiiHandler {

	@Override
	public void handleAcc(Acceleration acc) {
		System.out.println("Acceleration: x "+acc.x+" y "+acc.y+" z "+acc.z);

	}

	@Override
	public void handleButton(WiiButton b) {
		System.out.println("Button "+b.toString());
	}

}
