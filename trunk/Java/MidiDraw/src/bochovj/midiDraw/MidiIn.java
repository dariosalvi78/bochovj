/**
 * 
 */
package bochovj.midiDraw;

import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.MidiManagerIn;

/**
 * Just a wrapper that registers the DrawerApplet
 * 
 * @author bochovj
 *
 */
public class MidiIn extends MidiManagerIn {
    
    public void registerDrawerApplet(final DrawerApplet applet, final int blurChannel, final int blurControl, final int strWidthChannel, final int strWidthControl)
    {
	this.addHanler(new IMidiHandler() {
	    
	    @Override
	    public void handleNote(int channel, int note, int intensity) {
		
	    }
	    
	    @Override
	    public void handleControlChange(int channel, int controlN, int value) {
		if((channel == blurChannel)&&(controlN == blurControl))
		{
		    float blur = value / 127;
		    applet.handleBackGroundBlur(blur);
		}
		else if((channel == strWidthChannel)&&(controlN == strWidthControl))
		{
		    float strWidth= value / 127;
		    applet.handleStrokeWidth(strWidth);
		}
	    }
	});
    }

}
