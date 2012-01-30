/**
 * HIDApi: a library to manage HID devices
 * 
 * devices package: the package containing specific devices implementations
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package devices;

import java.util.Arrays;
import java.util.LinkedList;

import hidapi.hidApiJNA.HIDLibrary;
import hidapi.hidApiJNA.hid_device_info;

import com.sun.jna.Pointer;
import com.sun.jna.WString;

/**
 * Minia implements the potocol of the Minia HID device http://minitronics.net/
 * 
 * @author bochovj
 *
 */
public class Minia {

	public static int scanningFrequency = 500;
	
	short pid = 0;
	short vid = 0;

	boolean scan = false;

	public void open() throws Exception
	{
		final HIDLibrary lib = HIDLibrary.INSTANCE;
		hid_device_info info = lib.hid_enumerate(vid, pid);
		final Pointer device = lib.hid_open(info.vendor_id, info.product_id, info.serialNumber);
		if(device == null)
			throw new Exception("Cannot open device");

		device_path = info.path;
		device_manufacturer = info.manufacturer.toString();
		device_product_id = info.product_id;
		device_productName = info.productName.toString();
		device_serialNumber = info.serialNumber.toString();
		device_vendor_id = info.vendor_id;

		scan = true;
		new Thread(new Runnable() {

			@Override
			public void run() {

				while(scan)
				{
					byte[] data = new byte[20];
					int readData = lib.hid_read(device, data, 20);

					if(readData == -1)
					{
						WString error = lib.hid_error(device);
						System.out.println("Error: "+error.toString());
					}
					else
					{
						MiniaData miniadata = parseBytes(data);
						for(MiniaDataHandler h : handlers)
							h.Handle(miniadata);
					}
					try {
						Thread.sleep(scanningFrequency);
					} catch (InterruptedException e) {
						//Nothing to do here
					}
				}
			}
		}).start();
	}

	/** Platform-specific device path */
	public String device_path;
	/** Device Vendor ID */
	public short device_vendor_id;
	/** Device Product ID */
	public short device_product_id;
	/** Serial Number */
	public String device_serialNumber;
	/** Manufacturer String */
	public String device_manufacturer;
	/** Product string */
	public String device_productName;

	public void stop()
	{
		scan = false;
		//TODO Close sensor
	}

	public interface MiniaDataHandler
	{
		public void Handle(MiniaData data);
	}

	private LinkedList<MiniaDataHandler> handlers = new LinkedList<MiniaDataHandler>();


	public void addHandler(MiniaDataHandler handler)
	{
		handlers.add(handler);
	}


	public class MiniaData
	{
		public boolean digital1;
		public boolean digital2;
		public boolean digital3;
		public boolean digital4;

		public int analog1;
		public int analog2;
		public int analog3;
		public int analog4;
		public int analog5;
		public int analog6;

		public String bits;
	}

	private MiniaData parseBytes(byte[] data)
	{
		MiniaData retVal = new MiniaData();

		byte[] temp = Arrays.copyOf(data, data.length);
		data[0] = temp[7];
		data[1] = temp[6];
		data[2] = temp[5];
		data[3] = temp[4];
		data[4] = temp[3];
		data[5] = temp[2];
		data[6] = temp[1];
		data[7] = temp[0];

		retVal.bits = printBits(data);

		retVal.digital1 = (data[0] & 0x80) != 0;
		retVal.digital2 = (data[0] & 0x40) != 0;
		retVal.digital3 = (data[0] & 0x20) != 0;
		retVal.digital4 = (data[0] & 0x10) != 0;


		retVal.analog1 = 0;
		retVal.analog1 |= data[0] & 0xF; //Filters last 4 bits
		retVal.analog1 <<= 6; //Moves 6 (10-4) positions ahead
		int secondByte = data[1] & 0xFC; //Filters first 6 bits
		secondByte >>>= 2; //Cuts out last 2 (8-6) bits
		retVal.analog1 += secondByte;


		retVal.analog2 = 0;
		retVal.analog2 |= data[1] & 0x3; //Filters last 2 bits
		retVal.analog2 <<= 8; //Moves 8 (10-2) positions ahead
		retVal.analog2 |= data[2] & 0xFF; //Filters all 8 bits

		retVal.analog3 = 0;
		retVal.analog3 |= data[3] & 0xFF; //Filters all 8 bits
		retVal.analog3 <<= 2; //Moves 2 (10-8) positions ahead
		secondByte = data[4] & 0xC0; //Filters first 2 bits
		secondByte >>>= 6; //Cuts out last 6 (8-2) bits
		retVal.analog3 += secondByte;

		retVal.analog4 = 0;
		retVal.analog4 |= data[4] & 0x3F; //Filters last 6 bits
		retVal.analog4 <<= 4; //Moves 4 (10-6) positions ahead
		secondByte = data[5] & 0xF; //Filters first 4 bits
		secondByte >>>= 4; //Cuts out last 4 (8-4) bits
		retVal.analog4 += secondByte;


		retVal.analog5 = 0;
		retVal.analog5 |= data[5] & 0xF; //Filters last 4 bits
		retVal.analog5 <<= 6; //Moves 6 (10-4) positions ahead
		secondByte = data[6] & 0xFC; //Filters first 6 bits
		secondByte >>>= 2; //Cuts out last 2 (8-6) bits
		retVal.analog5 += secondByte;

		retVal.analog6 = 0;
		retVal.analog6 |= data[6] & 0x3; //Filters last 2 bits
		retVal.analog6 <<= 8; //Moves 8 (10-2) positions ahead
		retVal.analog6 |= data[7] & 0xFF; //Filters all 8 bits

		return retVal;
	}

	private static String printBits(byte[] data)
	{
		String retval = "";
		for(int i=0; i< data.length; i++)
		{
			byte b = data[i];
			String byteS = "";
			for (int z = 128 & 0xFF; z > 0; z >>= 1)
			{
				byteS+= ((b & z) == z) ? "1" : "0";
			}
			retval += byteS;
			retval += '\n';
		}
		return retval;
	}


}
