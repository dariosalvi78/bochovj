/**
 * SerialToMidi is a simple application that gets messages from a simple protocol
 * on the serial port and routes them to the midi system.
 * The protocol specification is:
 * <ul>
 * <li> one message per line (i.e. packets are separated by a carriage return)
 * <li> Control changes are represented as: cc ch 10 ctrl 12 val 130
 * <li> Notes ON are represented as: non ch 10 key 12 vel 120
 * <li> Notes OFF are represented as: noff ch 10 key 14 vel 120
 * <li> Aftertouches are represented as: at ch 10 key 14 tch 100
 * </ul>
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 */
package bochoVJ.midi.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.JOptionPane;

import bochoVJ.midi.MidiManagerOut;

import gnu.io.*;

/**
 * The serial to midi class implements the protocol and sends it to a MidiManagerOut.
 * The main method asks for the serial port, the midi interface and starts a little
 * dialog with start/stop buttons.
 * In order to run, it needs the native libraries of RTXT in the execution path.
 * 
 * @author bochovj
 *
 */
public class SerialToMidi {

	private static Logger logger = Logger.getLogger(SerialToMidi.class.getName());
	MidiManagerOut midiout;
	int midioutN;
	CommPortIdentifier portId;
	CommPort port;
	boolean keepreading;

	/**
	 * Standard constructor.
	 * @param port the name of the serial port, e.g. COM2, TTY1
	 * @param midiout, the midi device where to send messages
	 * @throws Exception
	 */
	public SerialToMidi(String port, int midiout) throws Exception
	{
		portId =CommPortIdentifier.getPortIdentifier(port);
		if(portId == null)
		{
			throw new Exception("Cannot open serial port "+port);
		}
		this.midioutN = midiout;
		this.midiout = new MidiManagerOut();
	}

	/**
	 * Lists available serial ports.
	 * @return
	 */
	public static List<String> getSerialPorts()
	{
		List<String> retVal = new ArrayList<String>();
		Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
		while(ports.hasMoreElements())
		{
			CommPortIdentifier port = ports.nextElement();
			if(port.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				retVal.add(port.getName());
			}
		}

		return retVal;
	}

	/**
	 * Starts gathering data from the serial port and sending it to the midi out
	 * @throws Exception
	 */
	public void start() throws Exception
	{
		midiout.startDevice(midioutN);

		port = (SerialPort) portId.open(
				"BochoVJ", // Name of the application asking for the port 
				10000   // Wait max. 10 sec. to acquire port
				);

		InputStream is= port.getInputStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		keepreading = true;

		new Thread(new Runnable() {

			@Override
			public void run() {
				while(keepreading)
				{
					String line = null;
					try {
						line = reader.readLine();
						String[] data = line.split(" ");
						//cc ch 10 ctrl 12 val 130
						if(data[0].equalsIgnoreCase("cc"))
						{
							int channel = Integer.parseInt(data[2]);
							int ctrl = Integer.parseInt(data[4]);
							int val = Integer.parseInt(data[6]);
							midiout.sendControlChange(channel, ctrl, val);
						}
						else if(data[0].equalsIgnoreCase("non"))
						{
							int channel = Integer.parseInt(data[2]);
							int key = Integer.parseInt(data[4]);
							int vel = Integer.parseInt(data[6]);
							midiout.sendNoteOn(channel, key, vel);
						}
					} catch (IOException e) {

					}catch(InvalidMidiDataException ie)
					{
						System.out.println("error");
					}
				}
			}
		}).start();

	}

	/**
	 * Stops acquiring data from the serial port and closes port and midi
	 */
	public void stop()
	{
		keepreading = false;
		port.close();
		midiout.close();
	}

	/**
	 * Starts an interactive and graphical way of using this utility
	 * @param args ignored
	 */
	public static void main(String[] args)
	{
		logger.info("Starting SerialToMidi");
		try{
			FileHandler fh = new FileHandler("serialToMidi.log");
			fh.setFormatter(new SimpleFormatter());
			logger.addHandler(fh);
			logger.setLevel(Level.ALL);

			String chosenPort = (String) JOptionPane.showInputDialog(null, "Select serial port",
					"Serial to Midi",
					JOptionPane.QUESTION_MESSAGE, 
					null, getSerialPorts().toArray(), null);

			logger.info("Chosen serial port "+chosenPort);

			int out = MidiManagerOut.promptUserSelectDevice();

			logger.info("Chosen midi port "+out);

			SerialToMidi sm = new SerialToMidi(chosenPort, out);
			SerialToMidiDialog dialog = new SerialToMidiDialog(sm);
			dialog.setVisible(true);
		}
		catch (Exception e) {
			logger.severe("Error: "+e.getMessage());
			System.exit(-1);
		}

	}

}
