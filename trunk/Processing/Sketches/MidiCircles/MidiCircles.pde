ArrayList circles = new ArrayList();

int mainRadius = 200;
int circY = 100;
int lastSecond;
boolean sign;
void setup()
{
  lastSecond = second();
 size(400,400);
 background(0);
 sign = true;
 circles.add(new MidiCircle(200,200,50,0.5));
}

void draw()
{
       
  for(int i = 0; i< circles.size(); i++)
  {
    ((MidiCircle)circles.get(i)).draw();
  }

}
