/*
Monster m = new Monster(150, 150, color( (int)random(255),(int)random(255),(int)random(255)), 0, 0);


float tangentx;
float tangenty;

void setup()
{
  background(0);
  size(400, 400);
  frameRate(25);
}


 
void draw() 
{ 
  background(0);
  
  m.move();
  m.drawMonster();
  
  if((tangentx!= 0) ||(tangenty != 0))
  {
    stroke(250);
    line(-round(tangentx),-round(tangenty),round(tangentx), round(tangenty));
    stroke(150);
    line(-round(tangenty),400+ round(tangentx),round(tangenty),400 -round(tangentx));
  }
    //Bounch
    if((m.xpos <=0)||(m.xpos >= width))
    {
      m.xvelocity = -m.xvelocity;
      m.rotationAngle = atan2(m.yvelocity, m.xvelocity);
    }
    
    if((m.ypos <= 0)||(m.ypos >= height))
    {
      m.yvelocity = -m.yvelocity;
      m.rotationAngle = atan2(m.yvelocity, m.xvelocity);
    }

}

void mouseClicked()
{
  tangentx = 0;
  tangenty = 0;
  
  
  println("RotateTangent");
  tangentx = random(100, 400);
  tangenty = random(100, 400);
  //println("X: "+ tangentx+"Y:"+tangenty);
  m.rotateToTangent(tangentx, tangenty);
  
  //m.rotateMonster(HALF_PI);
}
*/
