#include <Servo.h> 

boolean inUse = false;

int calibrationData[9][3] = {
  25, 70, 130,
  30, 60, 130,
  35, 42, 132,
  45, 30, 130,
  50, 20, 110,
  60, 10, 100,
  70, 79, 95,
  75, 79, 88, 
  80, 80, 87  
};

int vMin = 25;
int vMax = 80;

Servo hservo;
Servo vservo;

int h;
int v;

void setup() 
{ 
  Serial.begin(9600);
  hservo.attach(11);
  vservo.attach(10);

  randomSeed(analogRead(0));

  hservo.write(0);
  vservo.write(0);

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
      inUse = true;
    }
    if(strcmp(commandbuffer, "stop") ==0){
      Serial.println("Stopping");
      inUse = false;
      hservo.write(0);
      vservo.write(0);
    }
  }
  memset(commandbuffer, 0, (sizeof(commandbuffer)/sizeof(commandbuffer[0])));

  if(inUse){
    v = random(vMin, vMax);

    int hmin = getHMin(v);
    int hmax = getHMax(v);

    if(hmin <= hmax){
      h= random(hmin,hmax);
    }
    else{
      h= random(hmax,hmin);
    }
    move(h,v);

    int randDelay = random(1000, 5000);
    wait(randDelay);
  }
}


int getHMin(int v){
  for(int i=0; i< 8-1; i++){

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
  for(int i=0; i< 8-1; i++){
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

void move(int h,int v){

  Serial.print("Moving to ");
  Serial.print(h);
  Serial.print(", ");
  Serial.println(v);

  vservo.write(v);   
  hservo.write(h);
}

void wait(int millisecd){
  Serial.print("waiting ");
  Serial.println(millisecd);

  for(int i=0; i< millisecd; i+=100) {
    int hp = random(-2,2);
    int vp = random(-2,2);
    h = h+hp;
    v= v+vp;

    vservo.write(v);   
    hservo.write(h);
    delay(100);
  }
}



