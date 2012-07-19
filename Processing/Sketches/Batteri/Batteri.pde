int initialBatterios = 100;
Batterio[] batterios = new Batterio[initialBatterios];

void setup(){
  size(800, 600);
  background(0);
  for(int i=0; i< initialBatterios; i++){
   batterios[i] = new Batterio(int(random(0, width)), 
   int(random(0,height))); 
  }
}

void draw(){
  for(int i=0; i< initialBatterios; i++){
    Batterio b = batterios[i];
    fill(b.c);
    ellipse(b.x, b.y, 5, 5);
    b.move();
  }
}
