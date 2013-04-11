/**
 * HIDApiJ: a library to manage HID devices
 * 
 * NativeUtils dynamically loads a native library from the jar.
 * Adapted from http://frommyplayground.com/how-to-load-native-jni-library-from-jar
 *  
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package jarutils;
 
import hidapi.hidApiJNA.HIDLibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
 
/**
 * Simple library class for working with JNA.
 * 
 * @see http://frommyplayground.com/how-to-load-native-jni-library-from-jar
 *
 * @author Dario Salvi, adapted from Adam Heirnich <adam@adamh.cz>, http://www.adamh.cz
 */
public class NativeUtils {
 
    /**
     * Private constructor - this class will never be instanced
     */
    private NativeUtils() {
    }
 
    /**
     * Loads library from current JAR archive
     * 
     * The file from JAR is copied into system temporary directory and then loaded. The temporary file is deleted after exiting.
     * Method uses String as filename because the pathname is "abstract", not system-dependent.
     * 
     * @param filename The filename inside JAR as absolute path (beginning with '/'), e.g. /package/File.ext
     * @param clazz The interface that will be mapped to the loaded library through JNA
     * @return The instance of the clazz mapped to the native library
     * @throws IOException If temporary file creation or read/write operation fails
     * @throws IllegalArgumentException If source file (param path) does not exist
     * @throws IllegalArgumentException If the path is not absolute or if the filename is shorter than three characters (restriction of {@see File#createTempFile(java.lang.String, java.lang.String)}).
     */
    public static Object loadLibraryFromJar(String path, Class clazz) throws IOException {
 
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("The path to be absolute (start with '/').");
        }
 
        // Obtain filename from path
        String[] parts = path.split("/");
        String filename = (parts.length > 1) ? parts[parts.length - 1] : null;
 
        // Split filename to prexif and suffix (extension)
        String prefix = "";
        String suffix = null;
        if (filename != null) {
            parts = filename.split("\\.", 2);
            prefix = parts[0];
            suffix = (parts.length > 1) ? "."+parts[parts.length - 1] : null; // Thanks, davs! :-)
        }
 
        // Check if the filename is okay
        if (filename == null || prefix.length() < 3) {
            throw new IllegalArgumentException("The filename has to be at least 3 characters long.");
        }
        
 
        // Prepare temporary file
        File temp = File.createTempFile(prefix, suffix);
        //String tempDir = System.getProperty("java.io.tmpdir");
        //File temp = new File(tempDir+"/11"+filename);
        temp.deleteOnExit();     
        	
        // Prepare buffer for data copying
        byte[] buffer = new byte[1024];
        int readBytes;
 
        // Open and check input stream
        InputStream is = NativeUtils.class.getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException("File " + path + " was not found inside JAR.");
        }
 
        // Open output stream and copy data between source file in JAR and the temporary file
        OutputStream os = new FileOutputStream(temp);
        try {
            while ((readBytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }
        } finally {
            os.close();
            is.close();
        }
        
        if (!temp.exists()) {
            throw new FileNotFoundException("Could not copy native library to " + temp.getAbsolutePath());
        }
        
 
        // Finally, load the library
        String tempDir = System.getProperty("java.io.tmpdir");
        System.setProperty("jna.library.path",tempDir);
        
        String fileName = temp.getName();
        int suffIndex = fileName.indexOf(suffix);
        if(suffIndex != -1){
        	fileName = fileName.substring(0, suffIndex);
        }

        return Native.loadLibrary(fileName, clazz);
    }
}