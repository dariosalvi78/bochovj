/**
 * Distributed under the LGPL 3.0
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package hidapi;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;

/**
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


    public interface HIDLibrary extends Library
    {
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

	HIDLibrary INSTANCE = (HIDLibrary) Native.loadLibrary("hidapi",HIDLibrary.class);

    }
}
