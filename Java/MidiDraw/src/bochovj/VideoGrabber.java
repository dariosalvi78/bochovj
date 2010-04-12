/**
 * 
 */
package bochovj;

import java.awt.Image;

import javax.media.Buffer;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

/**
 * A simple grabber of images from a live video, a wrapper for JMF
 * 
 * @author bochovj
 *
 */
@SuppressWarnings("restriction")
public class VideoGrabber {

    Player player;
    FrameGrabbingControl grabber;

    private String mediaAddress = "vfw://0"; //Deafult value

    public void setMediaAddress(String addr)
    {
	this.mediaAddress = addr;
    }

    public void startCapture() throws Exception
    {
	MediaLocator ml = new MediaLocator("vfw://0");

	player = Manager.createRealizedPlayer(ml);
	player.start();
	grabber = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");
    }

    public Image captureImage()
    {
	Buffer buf = grabber.grabFrame();
	BufferToImage btoi = new BufferToImage((VideoFormat)buf.getFormat());
	Image img = btoi.createImage(buf);
	return img;
    }
    
    public void stop()
    {
	player.close();
    }

}
