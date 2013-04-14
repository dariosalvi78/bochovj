/**
 * Wrapper for wiimote libraries.
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.wii;

import jarutils.NativeUtils;
import jarutils.NativeUtils.OS;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import sun.org.mozilla.javascript.internal.NativeArray;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

import bochoVJ.wii.IWiiHandler.Acceleration;
import bochoVJ.wii.IWiiHandler.WiiButton;

/**
 * Wrapper for Wiiusej
 * http://code.google.com/p/wiiusej/
 * @author bochovj
 *
 */
public class WiiuseJManager implements IWiiManager {

	Wiimote wiimote;

	List<IWiiHandler> wiiHandlers;

	private double batteryLevel;

	/* (non-Javadoc)
	 * @see bochoVJ.wii.IWiiManager#getBatteryLevel()
	 */
	public double getBatteryLevel() {
		return batteryLevel;
	}

	public WiiuseJManager() throws Exception{
		wiiHandlers = new LinkedList<IWiiHandler>();
		
		//Extract native libs
		if(NativeUtils.detectOS() == OS.Windows){
			if(NativeUtils.detectArchtiecture() == 32){
				NativeUtils.extractLibraryFromJar("/natives/win32/wiiuse.dll");
				NativeUtils.extractLibraryFromJar("/natives/win32/WiiuseJ.dll");
			}
			else{
				NativeUtils.extractLibraryFromJar("/natives/win64/wiiuse.dll");
				NativeUtils.extractLibraryFromJar("/natives/win64/WiiuseJ.dll");
			}
		}
		else if (NativeUtils.detectOS() == OS.Unix){
			if(NativeUtils.detectArchtiecture() == 32){
				NativeUtils.extractLibraryFromJar("/natives/linux32/libwiiuse.so");
				NativeUtils.extractLibraryFromJar("/natives/linux32/libWiiuseJ.so");
			}
			else{
				NativeUtils.extractLibraryFromJar("/natives/linux64/libwiiuse.so");
				NativeUtils.extractLibraryFromJar("/natives/linux64/libWiiuseJ.so");
			}
		}
	}

	/* (non-Javadoc)
	 * @see bochoVJ.wii.IWiiManager#addHandler(bochoVJ.wii.IWiiHandler)
	 */
	public void addHandler(IWiiHandler han) {
		wiiHandlers.add(han);
	}

	@Override
	public void removeHandler(IWiiHandler han) {
		wiiHandlers.remove(han);
	}

	/* (non-Javadoc)
	 * @see bochoVJ.wii.IWiiManager#connect()
	 */
	public void connect() throws Exception {
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		if((wiimotes == null) ||(wiimotes.length == 0))
			throw new Exception("No WIIMote found");

		wiimote = wiimotes[0];

		System.out.println("Wii Mote Found ID "+ wiimote.getId());
		wiimote.activateMotionSensing();

		wiimote.addWiiMoteEventListeners(new WiimoteListener() {

			@Override
			public void onStatusEvent(StatusEvent arg0) {
				if(arg0 != null)
					batteryLevel = arg0.getBatteryLevel();
			}

			@Override
			public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
			}

			@Override
			public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
			}

			@Override
			public void onMotionSensingEvent(MotionSensingEvent arg0) {
				if(arg0 != null)
					for(IWiiHandler h : wiiHandlers)
					{
						Acceleration acc = new Acceleration();
						acc.x = arg0.getGforce().getX();
						acc.y = arg0.getGforce().getY();
						acc.z = arg0.getGforce().getZ();
						h.handleAcc(acc);
					}
			}

			@Override
			public void onIrEvent(IREvent arg0) {
			}

			@Override
			public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
			}

			@Override
			public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
			}

			@Override
			public void onExpansionEvent(ExpansionEvent arg0) {
			}

			@Override
			public void onDisconnectionEvent(DisconnectionEvent arg0) {
			}

			@Override
			public void onClassicControllerRemovedEvent(
					ClassicControllerRemovedEvent arg0) {
			}

			@Override
			public void onClassicControllerInsertedEvent(
					ClassicControllerInsertedEvent arg0) {
			}

			@Override
			public void onButtonsEvent(WiimoteButtonsEvent arg0) {

				if(arg0 != null)
					for(IWiiHandler h : wiiHandlers)
					{
						if(arg0.isButtonAJustPressed())
							h.handleButton(WiiButton.A);
						if(arg0.isButtonBJustPressed())
							h.handleButton(WiiButton.B);
						if(arg0.isButtonDownJustPressed())
							h.handleButton(WiiButton.DOWN);
						if(arg0.isButtonHomeJustPressed())
							h.handleButton(WiiButton.HOME);
						if(arg0.isButtonLeftJustPressed())
							h.handleButton(WiiButton.LEFT);
						if(arg0.isButtonMinusJustPressed())
							h.handleButton(WiiButton.MINUS);
						if(arg0.isButtonPlusJustPressed())
							h.handleButton(WiiButton.PLUS);
						if(arg0.isButtonRightJustPressed())
							h.handleButton(WiiButton.RIGHT);
						if(arg0.isButtonOneJustPressed())
							h.handleButton(WiiButton.ONE);
						if(arg0.isButtonTwoJustPressed())
							h.handleButton(WiiButton.TWO);
						if(arg0.isButtonUpJustPressed())
							h.handleButton(WiiButton.UP);
					}
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
