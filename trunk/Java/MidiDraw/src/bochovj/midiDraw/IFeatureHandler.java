package bochovj.midiDraw;

/**
 * 
 * @author bochovj
 *
 */
public interface IFeatureHandler {
    
    /**
     * Handles a feature result
     * @param extractor
     * @param value
     */
    void handleFeature(FeatureExtractor extractor, int value);

}
