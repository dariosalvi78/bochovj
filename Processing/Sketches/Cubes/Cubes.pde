float spin = 0.0;
int cubes = 5;

void setup() 
{
  size(800, 600, P3D);
  background(0);
  noStroke();
}

void draw() 
{
  //background(0);
  lights();
  
   for(int i =0; i< cubes; i++) {
   spin += 0.005;
   float col = ((float)i/cubes) * 255;
   
   fill(col, col, col, 100);
   pushMatrix();
   translate(width/2, height/2, 0);
   rotateX(PI/9 + spin + PI *((float)i/cubes));
   rotateY(PI/5 + spin + PI *((float)i/cubes));
   box(width/3);
   popMatrix();
  }
}
