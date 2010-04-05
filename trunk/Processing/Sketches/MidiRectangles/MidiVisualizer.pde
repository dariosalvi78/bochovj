class MidiVisualizer implements Receiver
{
  private MidiShape[] shapes;
  
  public MidiVisualizer()
  {
    shapes = new MidiShape[12];

  }
  
  public void send(MidiMessage message, long timeStamp) 
  {
   ShortMessage sm = (ShortMessage) message;
   if(sm instanceof ShortMessage)
   {
    switch(sm.getCommand())
    {   
      case ShortMessage.NOTE_ON:
        int note = sm.getData1()%12;
        int intensity = round((float)sm.getData2()*(width/127));
      	this.shapes[note] = new MidiShape((width/12)*note, intensity, 20, 20, 5);
      	break;
      case ShortMessage.CHANNEL_PRESSURE:
        //println("CP:"+sm.getData1()+", "+sm.getData2());
        break;
      
      case ShortMessage.CONTROL_CHANGE:
        break;
      case ShortMessage.NOTE_OFF:
        break;
      case ShortMessage.ACTIVE_SENSING:
        println("AS:"+sm.getData1()+", "+sm.getData2());
        break; 
      case ShortMessage.PROGRAM_CHANGE:
        break;
      default:
        break;
        
     }
    }
    else
    {
    	println("Strange stuff");
    }   		
   }
     
   public void close() 
   {   
   }
   
   public void draw()
   {
     for(int i =0; i<12; i++)
     {
       if(this.shapes[i]!=null)
       {
         this.shapes[i].draw();
       }
     }
   }
}

