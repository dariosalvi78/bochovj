import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

      
class MidiManager
{
  private MidiVisualizer vis;
  
  public MidiManager(MidiVisualizer vis)
  {
    this.vis = vis;
  }
  public void startDevice()
  {
    MidiDevice.Info[] aInfos = MidiSystem.getMidiDeviceInfo();
          for (int i = 0; i < aInfos.length; i++) {
          try {
           MidiDevice device = MidiSystem.getMidiDevice(aInfos[i]);
             boolean bAllowsInput = (device.getMaxTransmitters() != 0);
             boolean bAllowsOutput = (device.getMaxReceivers() != 0);
             System.out.println("" + i + "  "
                 + (bAllowsInput ? "IN " : "   ")
                 + (bAllowsOutput ? "OUT " : "    ")
                 + aInfos[i].getName() + ", " + aInfos[i].getVendor()
                 + ", " + aInfos[i].getVersion() + ", "
                 + aInfos[i].getDescription());
          }  
          catch (MidiUnavailableException e) {
             println("Midi device unavailable"+i);
             System.err.println(e);
          }
          }
          
      try{
        MidiDevice InputDevice = MidiSystem.getMidiDevice(aInfos[0]);
        InputDevice.open();
        Receiver r = MidiSystem.getReceiver();
        Transmitter t = InputDevice.getTransmitter();
        t.setReceiver(r);	
       
        Receiver r2 = this.vis;
        Transmitter t2 = InputDevice.getTransmitter();
        t2.setReceiver(r2);	
      }
      catch(MidiUnavailableException e)
      {
        println("Device "+0+" unavailabe");
      }   
  } 
}
      

