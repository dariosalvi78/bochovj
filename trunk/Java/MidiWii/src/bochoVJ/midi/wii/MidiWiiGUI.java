package bochoVJ.midi.wii;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;

import bochoVJ.midi.MidiManagerOut;
import bochoVJ.wii.IWiiHandler;
import bochoVJ.wii.IWiiHandler.Acceleration;
import bochoVJ.wii.IWiiHandler.WiiButton;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.Dimension;
import javax.swing.JLabel;

public class MidiWiiGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;
    private JButton startButton = null;
    private JButton stopButton = null;

    private WiiToMidi midiwii;  //  @jve:decl-index=0:

    private JButton buttonConfigure = null;

    private GraphPlotter graphPlotter = null;

    private JLabel buttonLabel = null;
    /**
     * This is the default constructor
     */
    public MidiWiiGUI() {
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
		graphPlotter.graphPoint(acc.x, Color.red,-Acceleration.MaxACC, Acceleration.MaxACC);
		graphPlotter.graphPoint(acc.y, Color.green,-Acceleration.MaxACC, Acceleration.MaxACC);
		graphPlotter.graphPoint(acc.z, Color.blue,-Acceleration.MaxACC, Acceleration.MaxACC);
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
	this.setSize(276, 166);
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
	    startButton.setBounds(new Rectangle(9, 7, 64, 26));
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
	    stopButton.setBounds(new Rectangle(81, 7, 64, 27));
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
	    buttonConfigure.setBounds(new Rectangle(155, 6, 101, 28));
	    buttonConfigure.setText("Config MIDI");
	    buttonConfigure.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
		    ConfigurationGUI gui = new ConfigurationGUI(MidiWiiGUI.this, midiwii.getCurrentConfig());
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
    private GraphPlotter getGraphPlotter() {
        if (graphPlotter == null) {
    	graphPlotter = new GraphPlotter();
    	graphPlotter.setBounds(new Rectangle(9, 53, 176, 65));
        }
        return graphPlotter;
    }

    public static void  main(String[] args)
    {
	final MidiWiiGUI window = new MidiWiiGUI();


	window.setVisible(true);

    }
}  //  @jve:decl-index=0:visual-constraint="10,10"
