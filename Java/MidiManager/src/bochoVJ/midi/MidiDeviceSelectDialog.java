package bochoVJ.midi;

import javax.swing.JPanel;
import javax.swing.ListModel;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.event.ListDataListener;

import java.awt.Rectangle;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Dimension;

public class MidiDeviceSelectDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JList midiDeviceList = null;
    private JButton okButton = null;
    private JLabel midiDeviceLabel = null;

    private String messageText;
    /**
     * @param owner
     */
    public MidiDeviceSelectDialog(Window owner, String messageText) {
	super(owner);
	this.messageText = messageText;
	initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
	this.setSize(470, 202);
	this.setContentPane(getJContentPane());
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
	if (jContentPane == null) {
	    midiDeviceLabel = new JLabel();
	    midiDeviceLabel.setBounds(new Rectangle(10, 8, 217, 16));
	    midiDeviceLabel.setText(messageText);
	    jContentPane = new JPanel();
	    jContentPane.setLayout(null);
	    jContentPane.add(getMidiOutList(), null);
	    jContentPane.add(getOkButton(), null);
	    jContentPane.add(midiDeviceLabel, null);
	}
	return jContentPane;
    }

    /**
     * This method initializes midiOutList	
     * 	
     * @return javax.swing.JList	
     */
    private JList getMidiOutList() {
        if (midiDeviceList == null) {
    	midiDeviceList = new JList();
    	midiDeviceList.setBounds(new Rectangle(13, 31, 343, 127));
        }
        return midiDeviceList;
    }
    
    public static class MidiDevice
    {
	public MidiDevice(int devNumber, String devDescr)
	{
	    deviceNumber = devNumber;
	    deviceDescription = devDescr;
	}
	
	public int deviceNumber;
	public String deviceDescription;
	
	public String toString()
	{
	    return deviceDescription;
	}
    }
    
    public void createMidiOutList(final List<MidiDevice> devices)
    {
	midiDeviceList.setModel(new ListModel() {
	    @Override
	    public void removeListDataListener(ListDataListener arg0) {
	    }
	    
	    @Override
	    public int getSize() {
		return devices.size();
	    }
	    
	    @Override
	    public Object getElementAt(int arg0) {
		return devices.get(arg0);
	    }
	    
	    @Override
	    public void addListDataListener(ListDataListener arg0) {
	    }
	});
	midiDeviceList.getSelectedValue();
    }
    /**
     * This method initializes okButton	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getOkButton() {
        if (okButton == null) {
    	okButton = new JButton();
    	okButton.setBounds(new Rectangle(369, 118, 85, 33));
    	okButton.setText("OK");
    	okButton.addActionListener(new java.awt.event.ActionListener() {
    	    public void actionPerformed(java.awt.event.ActionEvent e) {
    		int deviceIndex = ((MidiDevice)midiDeviceList.getSelectedValue()).deviceNumber;
    		handler.handle(deviceIndex);
    		setVisible(false);
    	    }
    	});
        }
        return okButton;
    }

   public static interface IDeviceHandler
   {
       void handle(int deviceNumber);
   }
   
   private IDeviceHandler handler;
   
   public void setDeviceHandler(IDeviceHandler handler)
   {
       this.handler = handler;
   }
}  //  @jve:decl-index=0:visual-constraint="10,10"
