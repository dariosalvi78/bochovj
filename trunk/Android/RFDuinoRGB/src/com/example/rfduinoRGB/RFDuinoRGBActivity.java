/*
 * Copyright (C) 2013 Dario Salvi
 * Based on Lann Martin's examples code at https://github.com/lann/RFDuinoTest
 * and Android BLE examples at http://developer.android.com/guide/topics/connectivity/bluetooth-le.html
 * 
 * Licensed under the Apache License, Version 2.0 as below
 *
 * This file incorporates work covered by the following notice:
 *
 *   Copyright (C) 2013 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.example.rfduinoRGB;


import java.util.UUID;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.example.rfduinoRGB.R;

/**
 * A simple Android application for controlling an RGB LED on the
 * RFduino (see http://www.rfduino.com/) with the ColorWheel sketch
 * (see https://github.com/RFduino/RFduino/tree/master/libraries/RFduinoBLE/examples/ColorWheel)
 * @author Dario Salvi
 *
 */
public class RFDuinoRGBActivity extends Activity {

	/**
	 * Button used to connect to the RFDuino
	 */
	private Button connectButton;
	
	/**
	 * RGB bars.
	 */
	private SeekBar redBar, greenBar, blueBar;

	/**
	 * The Bluetooth adapter.
	 */
	private BluetoothAdapter bluetoothAdapter;

	/**
	 * The GATT connection.
	 */
	private BluetoothGatt bluetoothGatt;

	/**
	 * The characteristic where to send data.
	 */
	private BluetoothGattCharacteristic sendCharacteristic;

	/**
	 * Service where to send data.
	 * Discovered empirically,
	 * it would be possible to find it programatically by parsing the GATT messages.
	 */
	private final String serviceUUID = "00002220-0000-1000-8000-00805f9b34fb";

	/**
	 * Characteristic where to send data.
	 * Discovered empirically,
	 * it would be possible to find it programatically by parsing the GATT messages.
	 */
	private final String sendCharacteristicUUID = "00002222-0000-1000-8000-00805f9b34fb";

	/**
	 * Timeout for searching for an RFduino.
	 */
	private static final long SCAN_PERIOD = 20000;

	/**
	 * Used to call functions asynchronously.
	 */
	private Handler handler;

	/**
	 * Tells if we are connected or not.
	 */
	private boolean connected;

	/**
	 * Used for logging.
	 */
	private static final String TAG = "RFDuinoRGB";


	public RFDuinoRGBActivity(){
		super();
		connected = false;
		handler = new Handler();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rfduino);

		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		connectButton = (Button) findViewById(R.id.connectButton);
		connectButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!connected){
					//If not connected, let's look for RFduino and connect to it
					connectButton.setEnabled(false);
					bluetoothAdapter.startLeScan(handleScan);
					
					//Program a to stop the scanning after some seconds
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							if(!connected){
								//Cancel scan only if not connected
								Log.d(TAG, "Scanning timeout, stopping it");
								bluetoothAdapter.stopLeScan(handleScan);
								//All graphical things must be run on the UI thread:
								RFDuinoRGBActivity.this.runOnUiThread(new Runnable() {
									@Override
									public void run() {
										connectButton.setEnabled(true);
										connected = false;
										connectButton.setText(R.string.connect);
									}
								});
							}
						}
					}, SCAN_PERIOD);

				} else {
					//If connected, the same button is used for disconnecting
					connected = false;
					connectButton.setText(R.string.connect);
					
					redBar.setEnabled(false);
					greenBar.setEnabled(false);
					blueBar.setEnabled(false);

					if(bluetoothGatt != null)
						bluetoothGatt.disconnect();
				}
			}
		});

		redBar = (SeekBar) findViewById(R.id.redBar);
		redBar.setEnabled(false);
		redBar.setMax(255);//RGB are sent as 1 byte each, values from 0 to 255
		redBar.setOnSeekBarChangeListener(barchangeListener);

		greenBar = (SeekBar) findViewById(R.id.greenBar);
		greenBar.setEnabled(false);
		greenBar.setMax(255);
		greenBar.setOnSeekBarChangeListener(barchangeListener);

		blueBar = (SeekBar) findViewById(R.id.blueBar);
		blueBar.setEnabled(false);
		blueBar.setMax(255);
		blueBar.setOnSeekBarChangeListener(barchangeListener);
	}
	
	OnSeekBarChangeListener barchangeListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			//When the value of a bar changes, send the values to RFduino
			sendColor();
		}
	};

	/**
	 * Sends the color to RFduino.
	 * Reads the values in the seekbars and sends them as 3 bytes.
	 */
	private void sendColor(){
		byte[] data = new byte[3];
		data[0] = (byte) redBar.getProgress();
		data[1] = (byte) greenBar.getProgress();
		data[2] = (byte) blueBar.getProgress();
		if(sendCharacteristic != null){
			sendCharacteristic.setValue(data);
			sendCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
			bluetoothGatt.writeCharacteristic(sendCharacteristic);
		}
	}

	/**
	 * Handles the scanning of BLE devices.
	 */
	LeScanCallback handleScan = new LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
			Log.d(TAG, "Found BTLE device: " + device.getName());
			if(device.getName().equalsIgnoreCase("RFduino")){
				//Got a RFduino !
				connected = true;
				bluetoothAdapter.stopLeScan(handleScan);

				Log.d(TAG, "Found RFDuino trying to connect to " + device.getAddress());
				bluetoothGatt = device.connectGatt(RFDuinoRGBActivity.this, true, new BluetoothGattCallback() {

					@Override
					public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
						if (newState == BluetoothProfile.STATE_CONNECTED) {
							Log.d(TAG, "Connected to RFduino, attempting to start service discovery:" +
									bluetoothGatt.discoverServices());
						} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
							Log.d(TAG, "Disconnected from RFduino.");
						}
					}

					@Override
					public void onServicesDiscovered(BluetoothGatt gatt, int status) {

						if (status == BluetoothGatt.GATT_SUCCESS) {						
							//Once the services have been discovered we can connect to the one
							//we know is used for receiving the RGB values
							BluetoothGattService serv = bluetoothGatt.getService(UUID.fromString(serviceUUID));
							sendCharacteristic = serv.getCharacteristic(UUID.fromString(sendCharacteristicUUID));
							
							//Now we assume that the device is fully connected
							RFDuinoRGBActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									//On the UI we shall enable the bars and the disconnect button
									connectButton.setEnabled(true);
									connectButton.setText(R.string.disconnect);

									redBar.setEnabled(true);
									greenBar.setEnabled(true);
									blueBar.setEnabled(true);
								}
							});
						}
					}
				});
			}
		}
	};

	@Override
	protected void onStop() {
		super.onStop();
		//When the application stops we disconnect
		bluetoothAdapter.stopLeScan(handleScan);
		if(bluetoothGatt != null)
			bluetoothGatt.disconnect();
	}

}
