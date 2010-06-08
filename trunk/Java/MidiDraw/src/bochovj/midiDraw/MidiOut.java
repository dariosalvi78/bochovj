package bochovj.midiDraw;

import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;

import bochoVJ.midi.MidiManagerOut;

/**
 * A wrapper that implements the feature handler
 * @author bochovj
 *
 */
public class MidiOut extends MidiManagerOut implements IFeatureHandler{

    private Map<FeatureExtractor, Integer> channels = new HashMap<FeatureExtractor, Integer>();
    private Map<FeatureExtractor, Integer> controls = new HashMap<FeatureExtractor, Integer>();
    
    public void setMidiNumbers(FeatureExtractor extractor, int channelNumber, int controlNumber)
    {
	channels.put(extractor, channelNumber);
	controls.put(extractor, controlNumber);
    }
    
    @Override
    public void handleFeature(FeatureExtractor extractor, int value) {
	try {
	    sendControlChange(channels.get(extractor), controls.get(extractor), value);
	} catch (InvalidMidiDataException e) {
	    e.printStackTrace();
	}
    }

}
