package OSC2MIDI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphPlotter extends JPanel {

	public GraphPlotter() {
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Color col : pointCounters.keySet())
		{
			for(int i=0; i< pointCounters.get(col).size(); i++)
			{
				g.setColor(col);
				g.fillOval(i, pointCounters.get(col).get(i), 2, 2);
			}
		}
	}

	private Map<Color,LinkedList<Integer>> pointCounters = new HashMap<Color,LinkedList<Integer>>();  //  @jve:decl-index=0:

	/**
	 * Graphs a point. Self scaled.
	 * @param value actual value of the point
	 * @param col color to be used
	 * @param minVal min value
	 * @param maxVal max value
	 */
	public void graphPoint(double value, Color col, double minVal, double maxVal)
	{

		int normalizedValue =  (int)(((value - minVal) /( maxVal - minVal)) * getHeight());


		if(pointCounters.get(col) == null)
		{
			pointCounters.put(col, new LinkedList<Integer>());
		}
		else
		{
			if(pointCounters.get(col).size() == getWidth())
			{
				pointCounters.get(col).remove(0);
			}
		}

		pointCounters.get(col).add(normalizedValue);

		repaint();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final JFrame frame = new JFrame();
		frame.setSize(300, 200);
		final GraphPlotter plotter = new GraphPlotter();
		frame.setContentPane(plotter);
		frame.setVisible(true);

		new Thread(new Runnable() {

			@Override
			public void run() {
				double valx = -100;
				double valy = -30;
				while(true)
				{
					valx = valx +1;
					if(valx == 100)
						valx =-100;
					plotter.graphPoint(valx, Color.red, -100, 100);

					valy ++;
					if(valy == 30)
						valy = -30;
					plotter.graphPoint(valy, Color.blue,-30, 30);
					valy ++;
					if(valy == 30)
						valy = -30;
					plotter.graphPoint(valy, Color.blue,-30, 30);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
