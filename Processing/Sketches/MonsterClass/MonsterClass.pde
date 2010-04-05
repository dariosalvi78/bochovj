class Monster
{
  public float xpos, ypos;
  public color colour;
  public float xvelocity, yvelocity;
  public float rotationAngle = 0;
  private float fatness = -0.5;
  private int step = 0;
  private int stepLength = 6;
  private boolean rotating = false;
  private float finalRotation;
  private float rotationLength = 0.25;
  private boolean clockWiseRotation = true;
  
  Monster(){}
  
  Monster (int startingX, int startingY,color col,float startVelX, float startVelY)
  {
    xpos = startingX;
    ypos = startingY;
    colour = col;
    xvelocity = startVelX;
    yvelocity = startVelY;
    this.rotationAngle = atan2(this.yvelocity, this.xvelocity);
  }
  
  void move()
  {
    //Position
    this.xpos = this.xpos + this.xvelocity;
    this.ypos = this.ypos + this.yvelocity;
    
    //Steps
    this.step +=2;
    this.step = this.step % this.stepLength;
    
    //Rotation
    if(this.rotating)
      stepRotate();
  }
  
  void drawMonster()
  {
    translate(round(xpos), round(ypos));
    rotate(this.rotationAngle);
    drawBody();
    drawLegs();
    rotate(-this.rotationAngle);
    translate(-round(xpos), -round(ypos));
  }
  
  
  void drawBody()
  {
    /*
    stroke(255);
    line(0,0,30, 0);
    */
    
    fill(colour);
    beginShape();
    stroke(255,255,255);
    strokeWeight(1);
    curveTightness(this.fatness);
    curveVertex(6,6);
    curveVertex(13,0);
    curveVertex(6,-6);
    curveVertex(0,-8);
    curveVertex(-6,-6);
    curveVertex(-13,0);    
    curveVertex(-6,6);
    curveVertex(0,8);
    curveVertex(6,6);
    curveVertex(13,0);
    curveVertex(6,-6);
    endShape();
    //Lines
    stroke(255-red(this.colour),255-blue(this.colour),255-green(this.colour));
    strokeWeight(3);
    noFill();
    curve(-16, 10, -6, 6, -6, -6, -16, -10);
    curve(-10, 10, 6, 6, 6, -6, -10, -10);
    strokeWeight(0);
    
  }
  
  void drawLegs()
  {
    strokeWeight(2);
    noFill();
    curve(-10, 0, 6, 6, 6+this.step, 18, -10+this.step, 24);
    curve(-10, 0, 6, -6, 6+this.stepLength-this.step, -18, -10+this.stepLength-this.step, -24);
    curve(-22, 0, -6, -6, -6+this.step, -18, -22+this.step, -24);
    curve(-22, 0, -6, 6, -6+this.stepLength-this.step, 18, -22+this.stepLength-this.step, 24);
  }
  
  void rotateMonster(float angle)
  {
    if(this.rotating == false)
    {
      this.rotating = true;
      float newAngle = atan2(sin(angle), cos(angle));
      
      this.finalRotation =  this.rotationAngle + newAngle;
      
      //Detect sign
      if(newAngle >0) 
        this.clockWiseRotation = true;
      else
        this.clockWiseRotation = false;
    }
  }
  
  void rotateToTangent(float x, float y)
  {
    if(this.rotating == false)
    {
      this.rotating = true;
      float directionAngle = atan2(y,x);
      
      if(abs(directionAngle - this.rotationAngle) < abs(-directionAngle-this.rotationAngle))
      {
        this.finalRotation =  directionAngle;
      }
      else
      {
        this.finalRotation =  -directionAngle;
      }
      if(this.finalRotation - this.rotationAngle >0) 
        this.clockWiseRotation = true;
      else
        this.clockWiseRotation = false;
    }
  }
    
  void stepRotate()
  {
    
    if(this.clockWiseRotation)
      this.rotationAngle = this.rotationAngle + this.rotationLength;
    else
      this.rotationAngle = this.rotationAngle - this.rotationLength;
    
    if(abs(this.finalRotation - this.rotationAngle) <= this.rotationLength)
    {
      this.rotating = false;
    }
    
    float module = sqrt( pow(this.xvelocity,2) + pow(this.yvelocity,2) );
    this.xvelocity = module * cos(this.rotationAngle);
    this.yvelocity = module * sin(this.rotationAngle);
  }
  
  boolean isTouching(Monster m)
  {
    float distance = sqrt( pow(m.xpos-this.xpos,2) + pow(m.ypos-this.ypos,2) );
    float minDist = 2* 20;
    if(distance <= minDist)
      return true;
    else
      return false;
  }
  
  boolean willBeTouching(Monster m, int steps)
  {
    float mestX = m.xpos+m.xvelocity*steps;
    float mestY = m.ypos+m.yvelocity*steps;
    float estX = this.xpos+this.xvelocity*steps;
    float estY = this.ypos+this.yvelocity*steps;
    float distance = sqrt( pow(estX-mestX,2) + pow(estY-mestY,2) );
    float minDist = 2* 20;
    if(distance <= minDist)
      return true;
    else
      return false;
  }
}
