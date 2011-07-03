/**
 * 
 */
package bochoVJ.midi.wii;

import bochoVJ.wii.IWiiHandler.WiiButton;


/**
 * @author bochovj
 *
 */
public class Configuration {

    public int buttonsChannel;

    public int buttonA;
    public int buttonB;

    public int buttonHome;

    public int buttonMinus;
    public int buttonPlus;

    public int buttonOne;
    public int buttonTwo;

    public int buttonDown;
    public int buttonUp;
    public int buttonRight;
    public int buttonLeft;

    public int accsChannel;
    public int accx;
    public int accy;
    public int accz;

    public int getButtonNote(WiiButton b)
    {
	switch(b)
	{
	case A:
	    return buttonA;
	case B:
	    return buttonB;
	case DOWN:
	    return buttonDown;
	case HOME:
	    return buttonHome;
	case LEFT:
	    return buttonLeft;
	case MINUS:
	    return buttonLeft;
	case ONE:
	    return buttonOne;
	case PLUS:
	    return buttonPlus;
	case RIGHT:
	    return buttonRight;
	case TWO:
	    return buttonTwo;
	case UP:
	    return buttonUp;
	default:
	    return -1;
	}
    }

    public static Configuration defaultConfiguration()
    {
	Configuration c = new Configuration();
	c.accsChannel = 1;
	c.accx = 1;
	c.accy = 2;
	c.accz = 3;

	c.buttonsChannel = 1;
	c.buttonDown = 1;
	c.buttonA = 2;
	c.buttonB = 3;
	c.buttonHome = 4;
	c.buttonLeft = 5;
	c.buttonMinus = 6;
	c.buttonOne = 7;
	c.buttonPlus = 8;
	c.buttonRight = 9;
	c.buttonTwo = 10;
	c.buttonUp = 11;

	return c;
    }
}
