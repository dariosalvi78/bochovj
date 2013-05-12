

void moveSlowly(int h, int v){
  float t = 1;
  float dist = sqrt(pow(lastH-h, 2) + pow(lastV-v, 2));
  float dt = 1/dist;

  while(t>=0 && state == inUse) {
    int hh = interpol(lastH, h, t);
    hservo.write(hh);
    int vv = interpol(lastV, v, t);
    vservo.write(vv);
    delay(50);
    
    t -= dt;
  }
  lastH = h;
  lastV = v;
}

/*
 Interpolates the segment between the points p1 (x1,y1)
 and p2 (x2, y2).
 t must be between 0 (corresponding to p2) and 1 (= p1)
*/
int interpol(int x1, int x2, float t){
  float x = t*x1 + (1-t)* x2;
  return (int) x;
}


void dither(int millisecd){
  for(int i=0; i< millisecd && state == inUse; i+=100) {
    int hp = random(-2,2);
    int vp = random(-2,2);

    hservo.write(lastH+hp);   
    vservo.write(lastV+vp);
    delay(100);
  }
}
