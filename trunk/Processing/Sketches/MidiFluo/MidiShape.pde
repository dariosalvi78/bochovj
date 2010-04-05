class MidiShape
{
 public int x,y,wid, heig;
 public float speed;
 private float step;
 
 public MidiShape(int x, int y, int wid, int heig, float speed)
 {
   this.x = x;
   this.y = y;
   this.wid = wid;
   this.heig = heig;
   this.speed = speed;
   this.step = 255;
 }
 
 public void draw()
 {
   fill(round(this.step));
   rect(x, y, wid, heig);
   
   if(this.step >0)
     this.step -= speed;
   else
     this.step = 0;
 }
}
