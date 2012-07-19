int pointMinSize = 10;
int pointMaxSize = 100;

int pointSize = pointMinSize;
boolean growing = true;

void setup()
{
  size(800, 600);
  background(0);
  frameRate(4);
}

void draw(){
 for(int i=0; i< height; i+=pointSize)
  for(int j=0; j<width; j+=pointSize){
   fill(random(0,255));
   rect(j,i,pointSize,pointSize);
  }
  if(growing)
    pointSize +=10;
  else
    pointSize -=10;
    
  if(pointSize >= pointMaxSize){
    growing = !growing;
  }
  if(pointSize <= pointMinSize){
    growing = !growing;
  }
}
