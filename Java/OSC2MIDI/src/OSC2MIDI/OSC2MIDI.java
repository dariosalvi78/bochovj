package OSC2MIDI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import bochoVJ.midi.MidiManagerOut;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

public class OSC2MIDI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private GraphPlotter graphPlotter = null;
	private JTable channelTable = null;

	private static final int midiChannel = 1;
	private int OSCInPort = 12345;

	HashMap<String, Integer> chanMap;
	HashMap<String, Float> mins;
	HashMap<String, Float> maxs;

	HashMap<String, Color> colors;
	/**
	 * This is the default constructor
	 */
	public OSC2MIDI() {
		super();

		chanMap = new HashMap<String, Integer>();
		mins = new HashMap<String, Float>();
		maxs = new HashMap<String, Float>();
		colors = new HashMap<String, Color>();
		initialize();
		startSystem();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setSize(488, 198);
		setContentPane(getJContentPane());
		setTitle("OSC 2 MIDI");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void startSystem() {
		final MidiManagerOut midiout = new MidiManagerOut();
		int devN = midiout.promptUserSelectDevice();
		try {
			midiout.startDevice(devN);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, "Error starting MIDI device, restart");
			System.exit(-1);
		}
		boolean repeat = true;
		while(repeat){
			String inPort = JOptionPane.showInputDialog("Select OSC incoming port", OSCInPort);
			try{
				OSCInPort = Integer.parseInt(inPort);
				repeat = false;
			}
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Input value must be integer, retry", "Input error", JOptionPane.ERROR_MESSAGE);
			}
		}


		OSCPortIn portin = null;
		try {
			portin = new OSCPortIn(OSCInPort);
		} catch (SocketException e1) {
			JOptionPane.showMessageDialog(this, "Error opening port "+OSCInPort+", restart");
			System.exit(-1);
		}
		portin.startListening();
		portin.addListener("/[A-Za-z0-9_-]*", new OSCListener() {

			@Override
			public void acceptMessage(Date arg0, OSCMessage mex) {
				String addr = mex.getAddress();

				if(!chanMap.containsKey(addr)){
					int ctrlN =0;
					do{
						Random ran = new Random();
						ctrlN = ran.nextInt(127);
					}
					while(chanMap.containsValue(ctrlN));
					chanMap.put(addr, ctrlN);
					System.out.println("Address "+addr+" mapped to ctrl N "+ctrlN);
					Random ran = new Random();
					colors.put(addr, new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
					getChannelTable().repaint();
					OSC2MIDI.this.repaint();
				}
				int ctrlN = chanMap.get(addr);
				float val = 0;
				for(Object o: mex.getArguments()){
					if(o instanceof Integer){
						val = (Integer)o;
						break;
					}
					if(o instanceof Float){
						val = (Float)o;
						break;
					}
					return;
				}

				if(!mins.containsKey(addr) || mins.get(addr)>val){
					mins.put(addr, val);
				}
				if(!maxs.containsKey(addr) || maxs.get(addr)<val){
					maxs.put(addr, val);
				}
				int mappedVal = (int) map(val, mins.get(addr), maxs.get(addr), 0, 127);
				getGraphPlotter().graphPoint(mappedVal, colors.get(addr), 0, 127);
				try {
					midiout.sendControlChange(midiChannel, ctrlN, mappedVal);
				} catch (InvalidMidiDataException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(2,1, 5, 5));
			jContentPane.add(getChannelTable(), null);
			jContentPane.add(getGraphPlotter(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes graphPlotter	
	 * 	
	 * @return OSC2MIDI.GraphPlotter	
	 */
	private GraphPlotter getGraphPlotter() {
		if (graphPlotter == null) {
			graphPlotter = new GraphPlotter();
		}
		return graphPlotter;
	}

	/**
	 * This method initializes channelTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getChannelTable() {
		if (channelTable == null) {
			TableModel model = 
				new AbstractTableModel() {
				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					if(columnIndex == 0){
						return chanMap.keySet().toArray()[rowIndex];
					}
					else if(columnIndex == 1){
						return midiChannel;
					}
					else if(columnIndex == 2){
						return chanMap.values().toArray()[rowIndex];
					}
					else return null;
				}

				@Override
				public Class getColumnClass(int columnIndex) {
					if(columnIndex == 0) return String.class;
					else if(columnIndex == 1) return Integer.class;
					else if(columnIndex == 2) return Integer.class;
					else return null;
				}

				@Override
				public String getColumnName(int columnIndex) {
					if(columnIndex == 0) return "OSC addr";
					else if(columnIndex == 1) return "MIDI chn";
					else if(columnIndex == 2) return "CTRL";
					else return null;
				}

				@Override
				public int getRowCount() {
					return chanMap.values().size();
				}

				@Override
				public int getColumnCount() {
					return 3;
				}

				@Override
				public boolean isCellEditable(int row, int col){ 
					if(col == 2) return true;
					else return false;
				}

				@Override
				public void setValueAt(Object aValue, int rowIndex,
						int columnIndex) {
					if(columnIndex == 2){
						try{
							Integer val = (Integer)aValue;
							if(chanMap.values().contains(val)){
								JOptionPane.showMessageDialog(OSC2MIDI.this, "Control N already ssigned !");
								return;
							}
							String key = (String) chanMap.keySet().toArray()[rowIndex];
							chanMap.put(key, val);
							System.out.println("OSC "+key+" set to "+val);
						} catch(ClassCastException ce){
							JOptionPane.showMessageDialog(OSC2MIDI.this, "Control N must be integer");
						}
					}
				}
			};
			channelTable = new JTable(model);
		}
		return channelTable;
	}

	static public final float map(float value,
			float start1, float stop1,
			float start2, float stop2) {
		return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
	}

	public static void main(String[] args){
		new OSC2MIDI().setVisible(true);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
