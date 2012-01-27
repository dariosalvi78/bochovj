/**
 * Wrapper for wiiRemoteJ
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.wii;

import java.util.LinkedList;
import java.util.List;

import bochoVJ.wii.IWiiHandler.Acceleration;
import bochoVJ.wii.IWiiHandler.WiiButton;

import wiiremotej.WiiRemote;
import wiiremotej.WiiRemoteExtension;
import wiiremotej.WiiRemoteJ;
import wiiremotej.event.WRAccelerationEvent;
import wiiremotej.event.WRButtonEvent;
import wiiremotej.event.WRCombinedEvent;
import wiiremotej.event.WRExtensionEvent;
import wiiremotej.event.WRIREvent;
import wiiremotej.event.WRStatusEvent;
import wiiremotej.event.WiiRemoteListener;

/**
 * Wrapper for WiiRemoteJ
 * 
 * @author bochovj
 *
 */
public class WiiRemoteJManager implements IWiiManager {

	WiiRemote wiimote;

	List<IWiiHandler> wiiHandlers;

	private double batteryLevel;

	/* (non-Javadoc)
	 * @see bochoVJ.wii.IWiiManager#getBatteryLevel()
	 */
	public double getBatteryLevel()
	{
		return batteryLevel;
	}

	public WiiRemoteJManager()
	{
		// Used for bluecove, the minimum psm must be set to off
		System.setProperty("bluecove.jsr82.psm_minimum_off", "true");
		//Let's have it verbous
		WiiRemoteJ.setConsoleLoggingAll();
		wiiHandlers = new LinkedList<IWiiHandler>();	
	}

	/* (non-Javadoc)
	 * @see bochoVJ.wii.IWiiManager#addHandler(bochoVJ.wii.IWiiHandler)
	 */
	public void addHandler(IWiiHandler han)
	{
		wiiHandlers.add(han);
	}
	
	@Override
	public void removeHandler(IWiiHandler han) {
		wiiHandlers.remove(han);
	}

	/* (non-Javadoc)
	 * @see bochoVJ.wii.IWiiManager#connect()
	 */
	public void connect() throws Exception
	{
		wiimote = WiiRemoteJ.findRemote();
		if(wiimote == null)
			throw new Exception("No WIIMote found");
		
		System.out.println("Wii Mote Found on: "+ wiimote.getBluetoothAddress());
		wiimote.setAccelerometerEnabled(true);
		wiimote.addWiiRemoteListener(new WiiRemoteListener() {

			@Override
			public void statusReported(WRStatusEvent arg0) {
				batteryLevel = arg0.getBatteryLevel();
			}

			@Override
			public void extensionUnknown() {
				// TODO Auto-generated method stub

			}

			@Override
			public void extensionPartiallyInserted() {
				// TODO Auto-generated method stub

			}

			@Override
			public void extensionInputReceived(WRExtensionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void extensionDisconnected(WiiRemoteExtension arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void extensionConnected(WiiRemoteExtension arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void disconnected() {
				// TODO Auto-generated method stub

			}

			@Override
			public void combinedInputReceived(WRCombinedEvent arg0) {
				if(arg0.getButtonEvent() != null)
					buttonInputReceived(arg0.getButtonEvent());

				if(arg0.getAccelerationEvent() != null)
					accelerationInputReceived(arg0.getAccelerationEvent());
			}

			/**
			 * Just translate events
			 */
			@Override
			public void buttonInputReceived(WRButtonEvent arg0) {
				if(arg0 != null)
					for(IWiiHandler h : wiiHandlers)
					{
						if(arg0.isPressed(WRButtonEvent.A))
							h.handleButton(WiiButton.A);
						if(arg0.isPressed(WRButtonEvent.B))
							h.handleButton(WiiButton.B);
						if(arg0.isPressed(WRButtonEvent.DOWN))
							h.handleButton(WiiButton.DOWN);
						if(arg0.isPressed(WRButtonEvent.HOME))
							h.handleButton(WiiButton.HOME);
						if(arg0.isPressed(WRButtonEvent.LEFT))
							h.handleButton(WiiButton.LEFT);
						if(arg0.isPressed(WRButtonEvent.MINUS))
							h.handleButton(WiiButton.MINUS);
						if(arg0.isPressed(WRButtonEvent.PLUS))
							h.handleButton(WiiButton.PLUS);
						if(arg0.isPressed(WRButtonEvent.RIGHT))
							h.handleButton(WiiButton.RIGHT);
						if(arg0.isPressed(WRButtonEvent.ONE))
							h.handleButton(WiiButton.ONE);
						if(arg0.isPressed(WRButtonEvent.TWO))
							h.handleButton(WiiButton.TWO);
						if(arg0.isPressed(WRButtonEvent.UP))
							h.handleButton(WiiButton.UP);
					}
			}

			/**
			 * Translates acceleration events
			 */
			@Override
			public void accelerationInputReceived(WRAccelerationEvent arg0) {
				if(arg0 != null)
					for(IWiiHandler h : wiiHandlers)
					{
						Acceleration acc = new Acceleration();
						acc.x = arg0.getXAcceleration();
						acc.y = arg0.getYAcceleration();
						acc.z = arg0.getZAcceleration();
						h.handleAcc(acc);
					}
			}

			@Override
			public void IRInputReceived(WRIREvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/* (non-Javadoc)
	 * @see bochoVJ.wii.IWiiManager#disconnect()
	 */
	public void disconnect()
	{
		if(wiimote != null)
			wiimote.disconnect();
	}

}
