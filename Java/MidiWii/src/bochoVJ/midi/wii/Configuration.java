/**
 * 
 */
package bochoVJ.midi.wii;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

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
	
	public int energy;

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

	public static Configuration loadFromFile() throws FileNotFoundException, IOException
	{
		File file = new File("configuration.properties");
		Properties configFile = new Properties();
		configFile.load(new FileReader(file));
		Configuration config = new Configuration();
		config.accsChannel = Integer.parseInt(configFile.getProperty("accsChannel"));
		config.accx = Integer.parseInt(configFile.getProperty("accx"));
		config.accy = Integer.parseInt(configFile.getProperty("accy"));
		config.accz = Integer.parseInt(configFile.getProperty("accz"));
		config.buttonA = Integer.parseInt(configFile.getProperty("buttonA"));
		config.buttonB = Integer.parseInt(configFile.getProperty("buttonB"));
		config.buttonDown = Integer.parseInt(configFile.getProperty("buttonDown"));
		config.buttonHome = Integer.parseInt(configFile.getProperty("buttonHome"));
		config.buttonLeft = Integer.parseInt(configFile.getProperty("buttonLeft"));
		config.buttonMinus = Integer.parseInt(configFile.getProperty("buttonMinus"));
		config.buttonOne = Integer.parseInt(configFile.getProperty("buttonOne"));
		config.buttonPlus = Integer.parseInt(configFile.getProperty("buttonPlus"));
		config.buttonRight = Integer.parseInt(configFile.getProperty("buttonRight"));
		config.buttonsChannel = Integer.parseInt(configFile.getProperty("buttonsChannel"));
		config.buttonTwo = Integer.parseInt(configFile.getProperty("buttonTwo"));
		config.buttonUp = Integer.parseInt(configFile.getProperty("buttonUp"));
		config.energy = Integer.parseInt(configFile.getProperty("energy"));
		
		return config;
	}
	
	
	public void saveToFile()
	{
		
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
		c.energy = 12;
		
		return c;
	}
}
