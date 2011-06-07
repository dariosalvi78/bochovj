package devices;


import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;

import devices.Minia.MiniaData;
import devices.Minia.MiniaDataHandler;

public class MiniaGUITester extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JButton startButton = null;
    private JButton stopButton = null;
    private JLabel DIgital1Label = null;
    private JLabel digital2Label = null;
    private JLabel digital3Label = null;
    private JLabel digital4Label = null;
    private JCheckBox digital1Check = null;
    private JCheckBox digital2Check = null;
    private JCheckBox digital3Check = null;
    private JCheckBox digital4Check = null;
    private JTextArea bitsTextArea = null;

    /**
     * This method initializes startButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getStartButton() {
	if (startButton == null) {
	    startButton = new JButton();
	    startButton.setBounds(new Rectangle(13, 21, 103, 32));
	    startButton.setText("Start");
	    startButton.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
		    try {
			minia.open();
		    } catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		}
	    });
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
	    stopButton.setBounds(new Rectangle(193, 19, 87, 32));
	    stopButton.setText("Stop");
	    stopButton.addActionListener(new java.awt.event.ActionListener() {
		public void actionPerformed(java.awt.event.ActionEvent e) {
		    minia.stop();
		}
	    });
	}
	return stopButton;
    }

    /**
     * This method initializes digital1Check	
     * 	
     * @return javax.swing.JCheckBox	
     */
    private JCheckBox getDigital1Check() {
	if (digital1Check == null) {
	    digital1Check = new JCheckBox();
	    digital1Check.setBounds(new Rectangle(173, 80, 21, 21));
	}
	return digital1Check;
    }

    /**
     * This method initializes digital2Check	
     * 	
     * @return javax.swing.JCheckBox	
     */
    private JCheckBox getDigital2Check() {
	if (digital2Check == null) {
	    digital2Check = new JCheckBox();
	    digital2Check.setBounds(new Rectangle(173, 100, 21, 21));
	}
	return digital2Check;
    }

    /**
     * This method initializes digital3Check	
     * 	
     * @return javax.swing.JCheckBox	
     */
    private JCheckBox getDigital3Check() {
	if (digital3Check == null) {
	    digital3Check = new JCheckBox();
	    digital3Check.setBounds(new Rectangle(173, 119, 21, 21));
	}
	return digital3Check;
    }

    /**
     * This method initializes digital4Check	
     * 	
     * @return javax.swing.JCheckBox	
     */
    private JCheckBox getDigital4Check() {
	if (digital4Check == null) {
	    digital4Check = new JCheckBox();
	    digital4Check.setBounds(new Rectangle(174, 138, 21, 21));
	}
	return digital4Check;
    }

    /**
     * This method initializes bitsTextArea	
     * 	
     * @return javax.swing.JTextArea	
     */
    private JTextArea getBitsTextArea() {
	if (bitsTextArea == null) {
	    bitsTextArea = new JTextArea();
	    bitsTextArea.setBounds(new Rectangle(16, 76, 64, 131));
	    bitsTextArea.setText("12345678");
	}
	return bitsTextArea;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub

	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		MiniaGUITester thisClass = new MiniaGUITester();
		thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thisClass.setVisible(true);
	    }
	});
    }


    private Minia minia;
    private JLabel analog1Label = null;
    private JLabel analog2Label = null;
    private JLabel analog3Label = null;
    private JLabel analog4Label = null;
    private JLabel analog5Label = null;
    private JLabel analog6Label = null;

    /**
     * This is the default constructor
     */
    public MiniaGUITester() {
	super();
	initialize();

	minia = new Minia();
	minia.addHandler(new MiniaDataHandler() {

	    @Override
	    public void Handle(MiniaData data) {
		bitsTextArea.setText(data.bits);

		digital1Check.setSelected(data.digital1);
		digital2Check.setSelected(data.digital2);
		digital3Check.setSelected(data.digital3);
		digital4Check.setSelected(data.digital4);

		analog1Label.setText("analog 1 "+data.analog1);
		analog2Label.setText("analog 2 "+data.analog2);
		analog3Label.setText("analog 3 "+data.analog3);
		analog4Label.setText("analog 4 "+data.analog4);
		analog5Label.setText("analog 5 "+data.analog5);
		analog6Label.setText("analog 6 "+data.analog6);
	    }
	});
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
	this.setSize(349, 271);
	this.setContentPane(getJContentPane());
	this.setTitle("Minia Tester GUI");
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
	if (jContentPane == null) {
	    analog6Label = new JLabel();
	    analog6Label.setBounds(new Rectangle(216, 192, 88, 16));
	    analog6Label.setText("");
	    analog5Label = new JLabel();
	    analog5Label.setBounds(new Rectangle(216, 171, 89, 16));
	    analog5Label.setText("");
	    analog4Label = new JLabel();
	    analog4Label.setBounds(new Rectangle(216, 150, 89, 16));
	    analog4Label.setText("");
	    analog3Label = new JLabel();
	    analog3Label.setBounds(new Rectangle(217, 128, 88, 16));
	    analog3Label.setText("");
	    analog2Label = new JLabel();
	    analog2Label.setBounds(new Rectangle(217, 107, 88, 16));
	    analog2Label.setText("");
	    analog1Label = new JLabel();
	    analog1Label.setBounds(new Rectangle(216, 86, 89, 16));
	    analog1Label.setText("");
	    digital4Label = new JLabel();
	    digital4Label.setBounds(new Rectangle(111, 139, 61, 16));
	    digital4Label.setText("Digital 4");
	    digital3Label = new JLabel();
	    digital3Label.setBounds(new Rectangle(113, 120, 60, 16));
	    digital3Label.setText("Digital 3");
	    digital2Label = new JLabel();
	    digital2Label.setBounds(new Rectangle(112, 102, 60, 16));
	    digital2Label.setText("Digital 2");
	    DIgital1Label = new JLabel();
	    DIgital1Label.setBounds(new Rectangle(111, 84, 60, 16));
	    DIgital1Label.setText("Digital 1");
	    jContentPane = new JPanel();
	    jContentPane.setLayout(null);
	    jContentPane.add(getStartButton(), null);
	    jContentPane.add(getStopButton(), null);
	    jContentPane.add(DIgital1Label, null);
	    jContentPane.add(digital2Label, null);
	    jContentPane.add(digital3Label, null);
	    jContentPane.add(digital4Label, null);
	    jContentPane.add(getDigital1Check(), null);
	    jContentPane.add(getDigital2Check(), null);
	    jContentPane.add(getDigital3Check(), null);
	    jContentPane.add(getDigital4Check(), null);
	    jContentPane.add(getBitsTextArea(), null);
	    jContentPane.add(analog1Label, null);
	    jContentPane.add(analog2Label, null);
	    jContentPane.add(analog3Label, null);
	    jContentPane.add(analog4Label, null);
	    jContentPane.add(analog5Label, null);
	    jContentPane.add(analog6Label, null);
	}
	return jContentPane;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
