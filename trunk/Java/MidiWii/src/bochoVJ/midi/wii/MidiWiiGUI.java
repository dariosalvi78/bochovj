package bochoVJ.midi.wii;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;

import bochoVJ.midi.MidiManagerOut;
import bochoVJ.wii.DummyTest;
import bochoVJ.wii.IWiiHandler;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import javax.swing.JLabel;
import java.awt.Dimension;

public class MidiWiiGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton startButton = null;
	private JButton stopButton = null;

	private WiiToMidi midiwii;  //  @jve:decl-index=0:

	private JButton buttonConfigure = null;

	private GraphPlotterInput graphPlotterInput = null;

	private JLabel buttonLabel = null;

	private JLabel lastButtonLabel = null;

	private JButton configAccsButton = null;

	private GraphPlotterInput graphPlotterMIDI = null;

	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public MidiWiiGUI() throws Exception {
		super();
		initialize();

		int deviceNumber = MidiManagerOut.promptUserSelectDevice();
		midiwii = new WiiToMidi(deviceNumber);
		midiwii.wiimng.addHandler(new IWiiHandler() {

			@Override
			public void handleButton(WiiButton b) {
				buttonLabel.setText(b.toString());
			}

			@Override
			public void handleAcc(Acceleration acc) {
				graphPlotterInput.graphPoint(acc.x, Color.red,-Acceleration.MaxACC, Acceleration.MaxACC);
				graphPlotterInput.graphPoint(acc.y, Color.green,-Acceleration.MaxACC, Acceleration.MaxACC);
				graphPlotterInput.graphPoint(acc.z, Color.blue,-Acceleration.MaxACC, Acceleration.MaxACC);
				
				graphPlotterMIDI.graphPoint(midiwii.accToControl(acc.x), Color.red,-Acceleration.MaxACC, Acceleration.MaxACC);
				graphPlotterMIDI.graphPoint(midiwii.accToControl(acc.y), Color.green,-Acceleration.MaxACC, Acceleration.MaxACC);
				graphPlotterMIDI.graphPoint(midiwii.accToControl(acc.z), Color.blue,-Acceleration.MaxACC, Acceleration.MaxACC);
			}
		});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("MidiWii");
		this.setSize(275, 278);
		this.setContentPane(getJContentPane());
		this.addWindowStateListener(new WindowStateListener() {

			@Override
			public void windowStateChanged(WindowEvent arg0) {
				if(arg0.getID() == WindowEvent.WINDOW_CLOSING)
				{
					midiwii.stop();
				}
			}
		});
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lastButtonLabel = new JLabel();
			lastButtonLabel.setBounds(new Rectangle(190, 47, 64, 27));
			lastButtonLabel.setText("last button");
			buttonLabel = new JLabel();
			buttonLabel.setBounds(new Rectangle(205, 76, 38, 30));
			buttonLabel.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getStartButton(), null);
			jContentPane.add(getStopButton(), null);
			jContentPane.add(getButtonConfigure(), null);

			jContentPane.add(getGraphPlotter(), null);
			jContentPane.add(buttonLabel, null);
			jContentPane.add(lastButtonLabel, null);
			jContentPane.add(getConfigAccsButton(), null);
			jContentPane.add(getGraphPlotterMIDI(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes startButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStartButton() {
		if (startButton == null) {
			startButton = new JButton();
			startButton.setBounds(new Rectangle(49, 8, 64, 26));
			startButton.setText("start");
			startButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						midiwii.start();
					} catch (Exception ex) {
						midiwii.stop();
						ex.printStackTrace();
						JOptionPane.showMessageDialog(MidiWiiGUI.this, "Error! :(");
					}

				}});
		}
		return startButton;
	}

	/**
	 * This method initializes stopButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStopButton() {
		if (stopButton == null) {
			stopButton = new JButton();
			stopButton.setBounds(new Rectangle(136, 8, 64, 27));
			stopButton.setText("stop");
			stopButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					midiwii.stop();
				}
			});
		}
		return stopButton;
	}


	/**
	 * This method initializes buttonConfigure	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButtonConfigure() {
		if (buttonConfigure == null) {
			buttonConfigure = new JButton();
			buttonConfigure.setBounds(new Rectangle(11, 198, 97, 28));
			buttonConfigure.setText("Config MIDI");
			buttonConfigure.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					MIDIConfigurationGUI gui = new MIDIConfigurationGUI(MidiWiiGUI.this, midiwii.getCurrentConfig());
					gui.setVisible(true);
					midiwii.configure(gui.getConfiguration());
				}
			});
		}
		return buttonConfigure;
	}

	/**
	 * This method initializes graphPlotter	
	 * 	
	 * @return bochoVJ.midi.wii.GraphPlotter	
	 */
	private GraphPlotterInput getGraphPlotter() {
		if (graphPlotterInput == null) {
			graphPlotterInput = new GraphPlotterInput();
			graphPlotterInput.setBounds(new Rectangle(13, 48, 170, 65));
		}
		return graphPlotterInput;
	}

	/**
	 * This method initializes configAccsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getConfigAccsButton() {
		if (configAccsButton == null) {
			configAccsButton = new JButton();
			configAccsButton.setBounds(new Rectangle(133, 198, 109, 29));
			configAccsButton.setText("Config Accs");
			configAccsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					AccsConfigurationGUI accsGui = new AccsConfigurationGUI(MidiWiiGUI.this, MidiWiiGUI.this.midiwii);
					accsGui.setVisible(true);
				}
			});
		}
		return configAccsButton;
	}

	/**
	 * This method initializes graphPlotterMIDI	
	 * 	
	 * @return bochoVJ.midi.wii.GraphPlotter	
	 */
	private GraphPlotterInput getGraphPlotterMIDI() {
		if (graphPlotterMIDI == null) {
			graphPlotterMIDI = new GraphPlotterInput();
			graphPlotterMIDI.setBounds(new Rectangle(11, 128, 233, 56));
		}
		return graphPlotterMIDI;
	}

	public static void  main(String[] args) throws Exception {
		final MidiWiiGUI window = new MidiWiiGUI();
		window.setVisible(true);
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
