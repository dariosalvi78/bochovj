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
import jarutils.NativeUtils.OS;

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

	/**
	 * Returns the singleton instance of the HID API.
	 * @return
	 * @throws Exception
	 */
	public static HIDLibrary getInstance() throws Exception{
		if(NativeUtils.detectOS() == OS.Windows){
			if(NativeUtils.detectArchtiecture() == 32){
				NativeUtils.extractLibraryFromJar("/natives/win32/hidapi.dll");
				return (HIDLibrary) Native.loadLibrary("hidapi", HIDLibrary.class);
			}
			else{
				NativeUtils.extractLibraryFromJar("/natives/win64/hidapi.dll");
				return (HIDLibrary) Native.loadLibrary("hidapi", HIDLibrary.class);
			}
		}
		throw new Exception("Don't have native libraries for this operating system");
	}

	/**
	 * A test to check that the library is loaded correctly.
	 * @param args ignored
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		short vid = 0x0000; //0x0A5C;
		short pid = 0x0001; //0x4502;
		System.out.println("Trying to acess device vid "+Integer.toHexString(vid).toUpperCase()+
				" pid "+Integer.toHexString(pid).toUpperCase());
		
		HIDLibrary lib = getInstance();
		hid_device_info info = lib.hid_enumerate(vid, pid);
		if(info == null){
			System.out.println("Cannot access device");
		}
		else{
			System.out.println("Device accessed: " + info.path);
		}
	}
}
