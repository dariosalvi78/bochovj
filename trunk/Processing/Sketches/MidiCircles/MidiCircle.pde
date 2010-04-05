class MidiCircle
{
  public int x, y, radius;
  private float step;
  private float speed;
  private int smallRadius = 10;
  private int Npetals = 6;
  
 public  MidiCircle(int x, int y, int radius, float speed)
 {
   this.x=x;
   this.y=y;
   this.radius = radius;
   this.step = 255;
   this.speed = speed;
 }
 
 public void draw()
 {
   if(this.step >0)
   {
     fill(this.step);
     for(int i =0; i <Npetals; i++)
     {
       int newY = this.y+i*round(this.radius*2/Npetals);
       if(newY >= this.y + this.radius/2)
       {
         newY = this.y-i*round(this.radius*2/Npetals);
         ellipse(getCircX(newY, this.radius, false),newY , this.smallRadius, this.smallRadius);
       }
       else
       {
         newY = this.y+i*round(this.radius*2/Npetals);
         ellipse(getCircX(newY, this.radius, true),newY , this.smallRadius, this.smallRadius);
       }
     }
     this.step -= this.speed;
   }
   else
     this.step = 0;
 }
 
 private int getCircX(int circY, int radius, boolean positive)
 {
  if(positive)
  return round(sqrt(pow(radius,2) - pow(circY-this.y, 2))+this.x);
  else
  return round(-sqrt(pow(radius,2) - pow(circY-this.y, 2))+this.x);
 }
}
