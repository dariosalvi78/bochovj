/**
 * 
 */
package bochoVJ.midi.wii;

import javax.swing.JPanel;
import java.awt.Frame;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import bochoVJ.midi.wii.WiiToMidi.TransformationFunction;


/**
 * @author bochoVJ
 *
 */
public class AccsConfigurationGUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	
	WiiToMidi wiimidi;
	private JLabel muteLabel = null;
	private JCheckBox muteAccxCheckBox = null;
	private JCheckBox muteAccyCheckBox = null;
	private JCheckBox muteAcczCheckBox = null;
	private JRadioButton ABSRadio = null;
	private JRadioButton LienarRadio = null;
	private JLabel transFunctionLabel = null;

	private ButtonGroup group = new ButtonGroup();
	
	/**
	 * @param owner
	 */
	public AccsConfigurationGUI(Frame owner, WiiToMidi wiimidi) {
		super(owner);
		initialize();
		
		group.add(ABSRadio);
		group.add(LienarRadio);
		
		setTitle("Accelerations Config");
		
		this.wiimidi = wiimidi;
		
		muteAccxCheckBox.setSelected(wiimidi.isMutedAccX());
		muteAccyCheckBox.setSelected(wiimidi.isMutedAccY());
		muteAcczCheckBox.setSelected(wiimidi.isMutedAccZ());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 129);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			transFunctionLabel = new JLabel();
			transFunctionLabel.setBounds(new Rectangle(15, 37, 154, 16));
			transFunctionLabel.setText("Transformation Function");
			muteLabel = new JLabel();
			muteLabel.setText("mute");
			muteLabel.setBounds(new Rectangle(11, 10, 50, 16));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(muteLabel, null);
			jContentPane.add(getMuteAccxCheckBox(), null);
			jContentPane.add(getMuteAccyCheckBox(), null);
			jContentPane.add(getMuteAcczCheckBox(), null);
			jContentPane.add(getABSRadio(), null);
			jContentPane.add(getLienarRadio(), null);
			jContentPane.add(transFunctionLabel, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes muteAccxCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getMuteAccxCheckBox() {
		if (muteAccxCheckBox == null) {
			muteAccxCheckBox = new JCheckBox();
			muteAccxCheckBox.setBounds(new Rectangle(68, 5, 55, 24));
			muteAccxCheckBox.setText("AccX");
			muteAccxCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					wiimidi.muteAccX(muteAccxCheckBox.isSelected());
				}
			});
		}
		return muteAccxCheckBox;
	}

	/**
	 * This method initializes muteAccyCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getMuteAccyCheckBox() {
		if (muteAccyCheckBox == null) {
			muteAccyCheckBox = new JCheckBox();
			muteAccyCheckBox.setBounds(new Rectangle(126, 6, 54, 24));
			muteAccyCheckBox.setText("AccY");
			muteAccyCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					wiimidi.muteAccY(muteAccyCheckBox.isSelected());
				}
			});
		}
		return muteAccyCheckBox;
	}

	/**
	 * This method initializes muteAcczCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getMuteAcczCheckBox() {
		if (muteAcczCheckBox == null) {
			muteAcczCheckBox = new JCheckBox();
			muteAcczCheckBox.setBounds(new Rectangle(183, 7, 54, 24));
			muteAcczCheckBox.setText("AccZ");
			muteAcczCheckBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					wiimidi.muteAccZ(muteAcczCheckBox.isSelected());
				}
			});
		}
		return muteAcczCheckBox;
	}

	/**
	 * This method initializes ABSRadio	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getABSRadio() {
		if (ABSRadio == null) {
			ABSRadio = new JRadioButton();
			ABSRadio.setBounds(new Rectangle(18, 57, 53, 21));
			ABSRadio.setText("ABS");
			ABSRadio.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if(ABSRadio.isSelected())
						wiimidi.setAccsFunction(TransformationFunction.ABS);
					else
						if(LienarRadio.isSelected())
							wiimidi.setAccsFunction(TransformationFunction.LINEAR);
				}
			});
		}
		return ABSRadio;
	}

	/**
	 * This method initializes LienarRadio	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getLienarRadio() {
		if (LienarRadio == null) {
			LienarRadio = new JRadioButton();
			LienarRadio.setBounds(new Rectangle(73, 58, 69, 21));
			LienarRadio.setText("Linear");
		}
		return LienarRadio;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
