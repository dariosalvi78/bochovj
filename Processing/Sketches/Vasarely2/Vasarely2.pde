
int barpos = 0;
int barwidth = 10;
float xoff = 0.0;

void setup(){
 size(800, 600);
 background(0);
 noStroke();
}

void draw(){
  float n = noise(xoff);
  float barc = map(barpos, 0, width, 0, 255);
  
  fill(barc, (255-barc) * n, n * 255);
  rect(barpos, 0, barwidth, height);
  
  xoff += 0.01;
  if(xoff == 10)
    xoff = 0;
    
  barpos +=10;
  if(barpos > width)
    barpos = 0;
}
