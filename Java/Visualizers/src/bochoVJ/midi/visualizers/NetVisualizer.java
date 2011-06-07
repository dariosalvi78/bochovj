package bochoVJ.midi.visualizers;

import processing.core.PApplet;
import bochoVJ.midi.IMidiHandler;
import bochoVJ.midi.applets.IMidiVisualizer;

public class NetVisualizer implements IMidiVisualizer {

	private class Node
	{
		public float X,Y;
		public float xSpeed, ySpeed;
		public int size;
	}

	PApplet app;

	Node[] nodes = new Node[12];

	int nodeSize = 20;
	int stimulationSize = 200;
	int stimulationDrop = 2;
	int connectionSize = 5;
	float connectingLenght = 200;

	float maxSpeed = 0.5F;

	public NetVisualizer(PApplet applet)
	{
		app = applet;

		for(int i=0; i< 12; i++)
		{
			nodes[i]= new Node();
			nodes[i].size = nodeSize;
			nodes[i].X = app.random(0, this.app.width);
			nodes[i].Y = app.random(0, this.app.height);
			float xspeed = app.random(0, maxSpeed);
			float yspeed = app.sqrt(app.pow(maxSpeed,2) - app.pow(xspeed,2));
			nodes[i].xSpeed = xspeed;
			nodes[i].ySpeed = yspeed;
		}
	}

	@Override
	public void draw() 
	{
		for(int i=0; i< 12; i++)
		{
			moveNode(nodes[i]);
			drawStimula(nodes[i]);
		}
		for(int i=0; i< 12; i++)
		{
			for(int j=i; j<12; j++)
				connectNode(nodes[i], nodes[j]);

			drawNode(nodes[i]);
		}
	}

	private void drawNode(Node n)
	{
		app.smooth();
		app.noStroke();
		app.fill(255);
		app.ellipse(n.X, n.Y, nodeSize, nodeSize);
		app.noSmooth();
	}

	private void drawStimula(Node n)
	{
		if(n.size > nodeSize)
		{
			n.size -= stimulationDrop;
			app.smooth();
			app.noStroke();
			app.fill(120,120,120,100);
			app.ellipse(n.X, n.Y, n.size, n.size);
			app.noSmooth();
		}
	}

	private void moveNode(Node n)
	{
		//move:
		n.X += n.xSpeed;
		n.Y += n.ySpeed;
		//bounch
		if((n.X <0)||(n.X >app.width))
			n.xSpeed = -n.xSpeed;
		if((n.Y <0)||(n.Y >app.height))
			n.ySpeed = -n.ySpeed;
	}

	private void connectNode(Node n1, Node n2)
	{
		float distance = app.sqrt(app.pow(n1.X - n2.X, 2)+app.pow(n1.Y - n2.Y, 2));
		if((distance<connectingLenght) && (n1!= n2))
		{
			drawConnection(app.round(n1.X), app.round(n1.Y), app.round(n2.X), app.round(n2.Y));
		}
	}

	private void drawConnection(int x1, int y1, int x2, int y2)
	{
		app.smooth();
		app.stroke(200);
		app.strokeWeight(connectionSize);
		app.line(x1, y1, x2, y2);
		app.noSmooth();
	}

	@Override
	public void handleNote(int channel, int note, int intensity) {
		int index= note%12;
		nodes[index].size = stimulationSize;
	}
	
	@Override
	public void handleControlChange(int channel, int controlN, int value) {
	    
	}
	

	@Override
	public void setup() {
		
	}

	

}
