class Batterio{
  public int x;
  public int y;
  public color c;
  
  Batterio(int x, int y){
    this.x=x;
    this.y=y;
    c = color(int(random(0,255)),
   int(random(0,255)),int(random(0,255)));
  }
  
  void move(){
   if(random(0,10) < 5)
       x--;
     else
       x++;
       
     if(x<=0)
       x=0;
     if(x>=width)
       x=width;
     
     if(random(0,10) < 5)
       y--;
     else
       y++;
       
     if(y<=0)
       y=0;
     if(y>=height)
       y=height;
  }
}
