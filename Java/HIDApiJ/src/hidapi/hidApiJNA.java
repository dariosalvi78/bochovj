/**
 * HIDApiJ: a library to manage HID devices
 * 
 * hidAPIJNA package contains the JNA wrapper of the HIDapi, version 0.5.2
 * http://www.signal11.us/oss/hidapi/
 * https://github.com/signal11/hidapi/downloads
 *  
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package hidapi;

import java.io.IOException;

import jarutils.NativeUtils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;

/**
 * A JNA wrapper of the HIDapi, version 0.5.2
 * http://www.signal11.us/oss/hidapi/
 *  
 * @author bochovj
 *
 */
public class hidApiJNA  {

	public static class hid_device_info extends Structure {

		/** Platform-specific device path */
		public String path;
		/** Device Vendor ID */
		public short vendor_id;
		/** Device Product ID */
		public short product_id;
		/** Serial Number */
		public WString serialNumber;
		/** Manufacturer String */
		public WString manufacturer;
		/** Product string */
		public WString productName;

		hid_device_info nextinfo;
	}


	public interface HIDLibrary extends Library {
		/**
		 * Enumerate the HID Devices.
		 * This function returns a linked list of all the HID devices
		 * attached to the system which match vendor_id and product_id.
		 * If vendor_id and product_id are both set to 0, then all HID
		 * devices will be returned.
		 * 
		 */
		hid_device_info hid_enumerate(short vendor_id, short product_id);

		Pointer hid_open(short vendor_id, short product_id, WString serial_number);

		int hid_read(Pointer device, byte[] data, int length);

		WString hid_error(Pointer device);
	}

	public static HIDLibrary getInstance() throws IOException{
		return (HIDLibrary) NativeUtils.loadLibraryFromJar("/natives/hidapi.dll", HIDLibrary.class);
	}

	/**
	 * A test to check that the library is loaded correctly.
	 * @param args ignored
	 */
	public static void main(String[] args) {
		try{
			HIDLibrary lib = getInstance();
			short id = 0x0A5C;
			short pid = 0x4502;
			hid_device_info info = lib.hid_enumerate(id, id);
			System.out.println("Device accessed !");
		}
		catch(Exception e){
			System.out.println("Error while loading native libraries ");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
