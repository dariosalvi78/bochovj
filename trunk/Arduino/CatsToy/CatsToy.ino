#include <Servo.h> 

boolean inUse = false;

const int calibrationVerts = 14;
int calibrationData[calibrationVerts][3] = {
  15, 150, 180,
  20, 83, 180,
  35, 57, 180,
  40, 37, 180,
  45, 30, 150,
  50, 25, 140,
  55, 18, 130,
  60, 15, 120,
  65, 10, 115,
  70, 15, 112,
  75, 85, 110,
  80, 97, 110,
  85, 95, 105,
  90, 95, 103,
};


Servo hservo;
Servo vservo;

int lastH;
int lastV;

void setup()  { 
  Serial.begin(9600);
  randomSeed(analogRead(0));
  inUse = false;
}

void loop()
{
  int i=0;
  char commandbuffer[100];

  if(Serial.available()){
    delay(100);
    while( Serial.available() && i< 99) {
      commandbuffer[i++] = Serial.read();
    }
  }

  if(i>0) {
    if(strcmp(commandbuffer, "start") ==0){
      Serial.println("Starting");
      startServos();
      inUse = true;
    }
    if(strcmp(commandbuffer, "stop") ==0){
      Serial.println("Stopping");
      stopServos();
      inUse = false;
    }
  }
  memset(commandbuffer, 0, (sizeof(commandbuffer)/sizeof(commandbuffer[0])));

  if(inUse){
    int vMin = calibrationData[0][0];
    int vMax = calibrationData[calibrationVerts-1][0];
    
    int v = random(vMin, vMax);

    int hmin = getHMin(v);
    int hmax = getHMax(v);
    
    Serial.print("Chosen V ");
    Serial.print(v);
    Serial.print(" Hmin ");
    Serial.print(hmin);
    Serial.print(" Hmax ");
    Serial.println(hmax);
    
    int h = 0;
    if(hmin <= hmax){
      h = random(hmin,hmax);
    }
    else{
      h= random(hmax,hmin);
    }
    Serial.print("Moving to v: ");
    Serial.print(v);
    Serial.print(" ,h: ");
    Serial.println(h);
    moveSlowly(h,v);

    int randDelay = random(1000, 5000);
    dither(randDelay);
  }
}

void startServos(){
  hservo.attach(11);
  vservo.attach(10);
  hservo.write(0);
  vservo.write(0);
  lastH=0;
  lastV=0;
}

void stopServos(){
  hservo.write(0);
  vservo.write(0);
  delay(1000);
  hservo.detach();
  vservo.detach();
  lastH=0;
  lastV=0;
}

int getHMin(int v){
  for(int i=0; i< calibrationVerts-1; i++){

    int v1 = calibrationData[i][0];
    int v2 = calibrationData[i+1][0];

    int h1 = calibrationData[i][1];
    int h2 = calibrationData[i+1][1];

    if(v == v1)
      return h1;
    if(v == v2)
      return h2;
    if((v> v1) && (v< v2))
      return map(v, v1, v2, h1, h2);
  }
  return -1;
}


int getHMax(int v){
  for(int i=0; i< calibrationVerts-1; i++){
    int v1 = calibrationData[i][0];
    int v2 = calibrationData[i+1][0];

    int h1 = calibrationData[i][2];
    int h2 = calibrationData[i+1][2];

    if(v == v1)
      return h1;
    if(v == v2)
      return h2;
    if((v> v1) && (v< v2))
      return map(v, v1, v2, h1, h2);
  }
  return -1;
}


void moveSlowly(int h, int v){
  int dh = h - lastH;
  int hincr = (dh>0)? 1:-1;
  
  int dv = v - lastV;
  int vincr = (dv>0)? 1:-1;
  
  for(int ddh = 0; ddh != dh; ddh += hincr)  
  {
    int hh = lastH + ddh;
    hservo.write(hh);
    delay(50);
  }
  hservo.write(h);
  lastH = h;

  for(int ddv = 0; ddv != dv; ddv += vincr)  
  {
    int vv = lastV + ddv;
    vservo.write(vv);
    delay(50);
  }
  vservo.write(v);
  lastV = v;
  
  Serial.print(" moved to v: ");
  Serial.print(lastV);
  Serial.print(" h: ");
  Serial.println(lastH);
}


void dither(int millisecd){
  Serial.print("... dithering randomly for ");
  Serial.print(millisecd);
  Serial.println(" msec");

  for(int i=0; i< millisecd; i+=100) {
    int hp = random(-2,2);
    int vp = random(-2,2);

    hservo.write(lastH+hp);   
    vservo.write(lastV+vp);
    delay(100);
  }
}



