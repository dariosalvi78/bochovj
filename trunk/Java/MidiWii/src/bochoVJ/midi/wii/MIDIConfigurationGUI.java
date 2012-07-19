package bochoVJ.midi.wii;

import javax.swing.JPanel;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;

public class MIDIConfigurationGUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel buttonALabel = null;
	private JLabel wiiPicture = null;
	private JTextField buttonAText = null;
	private JLabel buttonBLabel = null;
	private JTextField buttonBText = null;
	private JButton buttonOK = null;
	private JLabel buttonUPLabel = null;
	private JLabel buttonDownLabel = null;
	private JLabel buttonLEFTLabel = null;
	private JTextField buttonLeftText = null;
	private JLabel buttonRIGHTLabel = null;
	private JTextField buttonUPText = null;
	private JTextField buttonDOWNText = null;
	private JTextField buttonRIGHTText = null;
	private JLabel buttonMinusLabel = null;
	private JLabel buttonPLUSLabel = null;
	private JLabel buttonHOMELabel = null;
	private JLabel buttonONELabel = null;
	private JLabel buttonTWOLabel = null;
	private JTextField buttonMINUSText = null;
	private JTextField buttonPLUSText = null;
	private JTextField buttonONEText = null;
	private JTextField buttonTWOText = null;
	private JLabel accXLabel = null;
	private JLabel accYLabel = null;
	private JLabel accZLabel = null;
	private JTextField accXText = null;
	private JTextField accYText = null;
	private JTextField accZText = null;
	private JTextField buttonHOMEText = null;
	private JLabel accsLabel = null;
	private JTextField accsText = null;
	private JLabel buttonsLabel = null;
	private JTextField buttonsText = null;
	private JLabel energyLabel = null;
	private JTextField energyText = null;
	/**
	 * @param owner
	 */
	 public MIDIConfigurationGUI(Frame owner, Configuration c) {
		super(owner);
		initialize();

		buttonAText.setText(Integer.toString(c.buttonA));
		buttonBText.setText(Integer.toString(c.buttonB));
		buttonHOMEText.setText(Integer.toString(c.buttonHome));
		buttonMINUSText.setText(Integer.toString(c.buttonMinus));
		buttonPLUSText.setText(Integer.toString(c.buttonPlus));
		buttonUPText.setText(Integer.toString(c.buttonUp));
		buttonDOWNText.setText(Integer.toString(c.buttonDown));
		buttonLeftText.setText(Integer.toString(c.buttonLeft));
		buttonRIGHTText.setText(Integer.toString(c.buttonRight));

		buttonsText.setText(Integer.toString(c.buttonsChannel));

		accsText.setText(Integer.toString(c.accsChannel));
		accXText.setText(Integer.toString(c.accx));
		accYText.setText(Integer.toString(c.accy));
		accZText.setText(Integer.toString(c.accz));

		energyText.setText(Integer.toString(c.energy));
	 }

	 /**
	  * This method initializes this
	  * 
	  * @return void
	  */
	 private void initialize() {
		 this.setSize(233, 416);
		 this.setContentPane(getJContentPane());
	 }

	 /**
	  * This method initializes jContentPane
	  * 
	  * @return javax.swing.JPanel
	  */
	 private JPanel getJContentPane() {
		 if (jContentPane == null) {
			 energyLabel = new JLabel();
			 energyLabel.setBounds(new Rectangle(9, 326, 84, 16));
			 energyLabel.setText("ENERGY");
			 buttonsLabel = new JLabel();
			 buttonsLabel.setBounds(new Rectangle(7, 11, 96, 16));
			 buttonsLabel.setText("Buttons channel");
			 accsLabel = new JLabel();
			 accsLabel.setBounds(new Rectangle(4, 265, 128, 16));
			 accsLabel.setText("Acceleration channel");
			 accZLabel = new JLabel();
			 accZLabel.setBounds(new Rectangle(144, 297, 38, 16));
			 accZLabel.setText("ACCz");
			 accYLabel = new JLabel();
			 accYLabel.setBounds(new Rectangle(72, 297, 38, 16));
			 accYLabel.setText("ACCy");
			 accXLabel = new JLabel();
			 accXLabel.setBounds(new Rectangle(7, 295, 38, 16));
			 accXLabel.setText("ACCx");
			 buttonTWOLabel = new JLabel();
			 buttonTWOLabel.setBounds(new Rectangle(135, 208, 32, 16));
			 buttonTWOLabel.setText("TWO");
			 buttonONELabel = new JLabel();
			 buttonONELabel.setBounds(new Rectangle(4, 189, 31, 16));
			 buttonONELabel.setText("ONE");
			 buttonHOMELabel = new JLabel();
			 buttonHOMELabel.setBounds(new Rectangle(142, 167, 38, 16));
			 buttonHOMELabel.setText("HOME");
			 buttonPLUSLabel = new JLabel();
			 buttonPLUSLabel.setBounds(new Rectangle(141, 137, 38, 16));
			 buttonPLUSLabel.setText("PLUS");
			 buttonMinusLabel = new JLabel();
			 buttonMinusLabel.setBounds(new Rectangle(5, 132, 38, 16));
			 buttonMinusLabel.setText("MINUS");
			 buttonRIGHTLabel = new JLabel();
			 buttonRIGHTLabel.setBounds(new Rectangle(141, 68, 39, 16));
			 buttonRIGHTLabel.setText("RIGHT");
			 buttonLEFTLabel = new JLabel();
			 buttonLEFTLabel.setBounds(new Rectangle(143, 40, 33, 16));
			 buttonLEFTLabel.setText("LEFT");
			 buttonDownLabel = new JLabel();
			 buttonDownLabel.setBounds(new Rectangle(5, 67, 39, 16));
			 buttonDownLabel.setText("DOWN");
			 buttonUPLabel = new JLabel();
			 buttonUPLabel.setBounds(new Rectangle(12, 38, 17, 16));
			 buttonUPLabel.setText("UP");
			 buttonBLabel = new JLabel();
			 buttonBLabel.setBounds(new Rectangle(145, 100, 10, 16));
			 buttonBLabel.setText("B");
			 wiiPicture = new JLabel();
			 wiiPicture.setBounds(new Rectangle(74, 33, 58, 220));
			 wiiPicture.setText("");
			 wiiPicture.setIcon(new ImageIcon(getClass().getResource("WiiMote.jpg")));
			 buttonALabel = new JLabel();
			 buttonALabel.setBounds(new Rectangle(24, 96, 12, 16));
			 buttonALabel.setText("A");
			 jContentPane = new JPanel();
			 jContentPane.setLayout(null);
			 jContentPane.add(buttonALabel, null);
			 jContentPane.add(wiiPicture, null);
			 jContentPane.add(getButtonAText(), null);
			 jContentPane.add(buttonBLabel, null);
			 jContentPane.add(getButtonBText(), null);
			 jContentPane.add(getButtonOK(), null);
			 jContentPane.add(buttonUPLabel, null);
			 jContentPane.add(buttonDownLabel, null);
			 jContentPane.add(buttonLEFTLabel, null);
			 jContentPane.add(getButtonLeftText(), null);
			 jContentPane.add(buttonRIGHTLabel, null);
			 jContentPane.add(getButtonUPText(), null);
			 jContentPane.add(getButtonDOWNText(), null);
			 jContentPane.add(getButtonRIGHTText(), null);
			 jContentPane.add(buttonMinusLabel, null);
			 jContentPane.add(buttonPLUSLabel, null);
			 jContentPane.add(buttonHOMELabel, null);
			 jContentPane.add(buttonONELabel, null);
			 jContentPane.add(buttonTWOLabel, null);
			 jContentPane.add(getButtonMINUSText(), null);
			 jContentPane.add(getButtonPLUSText(), null);
			 jContentPane.add(getButtonONEText(), null);
			 jContentPane.add(getButtonTWOText(), null);
			 jContentPane.add(accXLabel, null);
			 jContentPane.add(accYLabel, null);
			 jContentPane.add(accZLabel, null);
			 jContentPane.add(getAccXText(), null);
			 jContentPane.add(getAccYText(), null);
			 jContentPane.add(getAccZText(), null);
			 jContentPane.add(getButtonHOMEText(), null);
			 jContentPane.add(accsLabel, null);
			 jContentPane.add(getAccsText(), null);
			 jContentPane.add(buttonsLabel, null);
			 jContentPane.add(getButtonsText(), null);
			 jContentPane.add(energyLabel, null);
			 jContentPane.add(getEnergyText(), null);
		 }
		 return jContentPane;
	 }

	 /**
	  * This method initializes buttonAText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonAText() {
		 if (buttonAText == null) {
			 buttonAText = new JTextField();
			 buttonAText.setBounds(new Rectangle(39, 94, 22, 20));
			 buttonAText.setText("5");
		 }
		 return buttonAText;
	 }

	 /**
	  * This method initializes buttonBText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonBText() {
		 if (buttonBText == null) {
			 buttonBText = new JTextField();
			 buttonBText.setBounds(new Rectangle(158, 99, 24, 20));
			 buttonBText.setText("6");
		 }
		 return buttonBText;
	 }

	 /**
	  * This method initializes buttonOK	
	  * 	
	  * @return javax.swing.JButton	
	  */
	 private JButton getButtonOK() {
		 if (buttonOK == null) {
			 buttonOK = new JButton();
			 buttonOK.setBounds(new Rectangle(127, 346, 75, 24));
			 buttonOK.setText("OK");
			 buttonOK.addActionListener(new ActionListener() {

				 @Override
				 public void actionPerformed(ActionEvent e) {
					 MIDIConfigurationGUI.this.setVisible(false);
				 }
			 });
		 }
		 return buttonOK;
	 }

	 /**
	  * This method initializes buttonLeftText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonLeftText() {
		 if (buttonLeftText == null) {
			 buttonLeftText = new JTextField();
			 buttonLeftText.setBounds(new Rectangle(179, 36, 23, 20));
			 buttonLeftText.setText("3");
		 }
		 return buttonLeftText;
	 }

	 /**
	  * This method initializes buttonUPText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonUPText() {
		 if (buttonUPText == null) {
			 buttonUPText = new JTextField();
			 buttonUPText.setBounds(new Rectangle(34, 38, 26, 20));
			 buttonUPText.setText("1");
		 }
		 return buttonUPText;
	 }

	 /**
	  * This method initializes buttonDOWNText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonDOWNText() {
		 if (buttonDOWNText == null) {
			 buttonDOWNText = new JTextField();
			 buttonDOWNText.setBounds(new Rectangle(47, 65, 23, 20));
			 buttonDOWNText.setText("2");
		 }
		 return buttonDOWNText;
	 }

	 /**
	  * This method initializes buttonRIGHTText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonRIGHTText() {
		 if (buttonRIGHTText == null) {
			 buttonRIGHTText = new JTextField();
			 buttonRIGHTText.setBounds(new Rectangle(185, 65, 22, 20));
			 buttonRIGHTText.setText("4");
		 }
		 return buttonRIGHTText;
	 }

	 /**
	  * This method initializes buttonMINUSText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonMINUSText() {
		 if (buttonMINUSText == null) {
			 buttonMINUSText = new JTextField();
			 buttonMINUSText.setBounds(new Rectangle(47, 133, 19, 20));
			 buttonMINUSText.setText("7");
		 }
		 return buttonMINUSText;
	 }

	 /**
	  * This method initializes buttonPLUSText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonPLUSText() {
		 if (buttonPLUSText == null) {
			 buttonPLUSText = new JTextField();
			 buttonPLUSText.setBounds(new Rectangle(184, 136, 22, 20));
			 buttonPLUSText.setText("8");
		 }
		 return buttonPLUSText;
	 }

	 /**
	  * This method initializes buttonONEText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonONEText() {
		 if (buttonONEText == null) {
			 buttonONEText = new JTextField();
			 buttonONEText.setBounds(new Rectangle(40, 187, 22, 20));
			 buttonONEText.setText("10");
		 }
		 return buttonONEText;
	 }

	 /**
	  * This method initializes buttonTWOText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonTWOText() {
		 if (buttonTWOText == null) {
			 buttonTWOText = new JTextField();
			 buttonTWOText.setBounds(new Rectangle(172, 205, 28, 20));
			 buttonTWOText.setText("11");
		 }
		 return buttonTWOText;
	 }

	 /**
	  * This method initializes accXText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getAccXText() {
		 if (accXText == null) {
			 accXText = new JTextField();
			 accXText.setBounds(new Rectangle(47, 295, 19, 20));
			 accXText.setText("1");
		 }
		 return accXText;
	 }

	 /**
	  * This method initializes accYText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getAccYText() {
		 if (accYText == null) {
			 accYText = new JTextField();
			 accYText.setBounds(new Rectangle(117, 295, 20, 20));
			 accYText.setText("2");
		 }
		 return accYText;
	 }

	 /**
	  * This method initializes accZText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getAccZText() {
		 if (accZText == null) {
			 accZText = new JTextField();
			 accZText.setBounds(new Rectangle(184, 294, 21, 20));
			 accZText.setText("3");
		 }
		 return accZText;
	 }
	 
	 private JTextField getEnergyText() {
		 if (energyText == null) {
			 energyText = new JTextField();
			 energyText.setBounds(new Rectangle(101, 322, 23, 20));
			 energyText.setText("12");
		 }
		 return energyText;
	 }

	 /**
	  * This method initializes buttonHOMEText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonHOMEText() {
		 if (buttonHOMEText == null) {
			 buttonHOMEText = new JTextField();
			 buttonHOMEText.setBounds(new Rectangle(185, 167, 25, 20));
			 buttonHOMEText.setText("9");
		 }
		 return buttonHOMEText;
	 }

	 /**
	  * This method initializes accsText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getAccsText() {
		 if (accsText == null) {
			 accsText = new JTextField();
			 accsText.setBounds(new Rectangle(141, 264, 25, 20));
			 accsText.setText("1");
		 }
		 return accsText;
	 }

	 /**
	  * This method initializes buttonsText	
	  * 	
	  * @return javax.swing.JTextField	
	  */
	 private JTextField getButtonsText() {
		 if (buttonsText == null) {
			 buttonsText = new JTextField();
			 buttonsText.setBounds(new Rectangle(113, 8, 21, 20));
			 buttonsText.setText("1");
		 }
		 return buttonsText;
	 }

	 public Configuration getConfiguration()
	 {
		 Configuration conf = new Configuration();

		 conf.buttonDown = Integer.parseInt(buttonDOWNText.getText());
		 conf.buttonA = Integer.parseInt(buttonAText.getText());
		 conf.buttonB = Integer.parseInt(buttonBText.getText());
		 conf.buttonHome = Integer.parseInt(buttonHOMEText.getText());
		 conf.buttonLeft = Integer.parseInt(buttonLeftText.getText());
		 conf.buttonMinus = Integer.parseInt(buttonMINUSText.getText());
		 conf.buttonOne = Integer.parseInt(buttonONEText.getText());
		 conf.buttonPlus = Integer.parseInt(buttonPLUSText.getText());
		 conf.buttonRight = Integer.parseInt(buttonRIGHTText.getText());
		 conf.buttonTwo = Integer.parseInt(buttonTWOText.getText());
		 conf.buttonUp = Integer.parseInt(buttonUPText.getText());

		 conf.buttonsChannel = Integer.parseInt(buttonsText.getText());

		 conf.accsChannel = Integer.parseInt(accsText.getText());
		 conf.accx = Integer.parseInt(accXText.getText());
		 conf.accy = Integer.parseInt(accYText.getText());
		 conf.accz = Integer.parseInt(accZText.getText());

		 conf.energy = Integer.parseInt(energyText.getText());
		 return conf;
	 }

}  //  @jve:decl-index=0:visual-constraint="10,10"
