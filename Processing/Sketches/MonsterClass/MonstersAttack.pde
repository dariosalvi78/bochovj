int Nmonsters = 16;

Monster[] monsters = new Monster[Nmonsters];


void setup()
{
  background(0);
  size(800, 600);
  frameRate(25);
  
  int rows = (int) sqrt( Nmonsters );
  for(int i = 0; i < rows; i++)
  {
    for(int j = 0; j < rows; j++)
    {
      monsters[i+j*rows] = new Monster(i*(width/rows), j*(height/rows), color( (int)random(255),(int)random(255),(int)random(255)), 2, 1);
    }
  }
}


 
void draw() 
{ 
  background(0);
  smooth();
  for(int i = 0; i < Nmonsters; i++)
  {
    monsters[i].move();
    monsters[i].drawMonster();
    
    //Bounch
    if((round(monsters[i].xpos) <=0)||(round(monsters[i].xpos) >= width))
    {
      monsters[i].xvelocity = -monsters[i].xvelocity;
      monsters[i].rotationAngle = atan2(monsters[i].yvelocity, monsters[i].xvelocity);
    }
    
    if((round(monsters[i].ypos) <= 0)||(round(monsters[i].ypos) >= height))
    {
      monsters[i].yvelocity = -monsters[i].yvelocity;
      monsters[i].rotationAngle = atan2(monsters[i].yvelocity, monsters[i].xvelocity);
    }
    
    for(int j = 0; j < Nmonsters; j++)
    {
      if((monsters[i].willBeTouching(monsters[j],10)) && (i!=j))
      {
        float tangX = monsters[i].ypos - monsters[j].ypos;
        float tangY = monsters[j].xpos - monsters[i].xpos;

        monsters[i].rotateToTangent(tangX, tangY);
        //monsters[i].colour = color( (int)random(255),(int)random(255),(int)random(255));
      }
    }

  }

}




