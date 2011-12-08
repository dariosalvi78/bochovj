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

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A GRaphical frontend to the SerialToMidi
 * 
 * Distributed under artistic license:
 * http://www.opensource.org/licenses/artistic-license-2.0.php
 * 
 * @author bochoVJ
 *
 */
public class SerialToMidiDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public SerialToMidiDialog(final SerialToMidi stm) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setTitle("Serial to Midi");
		setBounds(100, 100, 193, 82);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnStart = new JButton("Start");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						stm.start();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(SerialToMidiDialog.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btnStart.setBounds(10, 11, 71, 23);
			contentPanel.add(btnStart);
		}
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stm.stop();
			}
		});
		btnStop.setBounds(93, 11, 78, 23);
		contentPanel.add(btnStop);
	}
	
}
