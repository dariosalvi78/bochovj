#include <Servo.h> 

Servo hservo;
Servo vservo;

int hServoPIN = 11;
int vServoPIN = 10;

typedef enum stateType { idle, starting, stopping , inUse };
volatile stateType state;

int lastH;
int lastV;

const int calibrationVerts = 14;
int calibrationData[calibrationVerts][3] = {
  15, 105, 180,
  20, 85, 180,
  35, 50, 180,
  40, 45, 180,
  45, 37, 150,
  50, 35, 140,
  55, 25, 125,
  60, 22, 120,
  65, 20, 120,
  70, 15, 120,
  75, 95, 120,
  80, 105, 120,
  85, 105, 113,
  88, 105, 112,
};

void setup()  { 
  Serial.begin(9600);
  randomSeed(analogRead(0));
  state = idle;
  
  attachInterrupt(0, pushButton, RISING);
}

void loop()
{
  parseSerial();
  
  if(state == starting){
   Serial.println("Starting");
      startServos();
      state = inUse;
  } 
  else if(state == stopping){
    Serial.println("Stopping");
      stopServos();
      state = idle;
  } 
  else if (state == inUse){
    int vMin = calibrationData[0][0];
    int vMax = calibrationData[calibrationVerts-1][0];
    
    int v = random(vMin, vMax);

    int hmin = getHMin(v);
    int hmax = getHMax(v);
    
    int h = 0;
    if(hmin <= hmax){
      h = random(hmin,hmax);
    } else{
      h= random(hmax,hmin);
    }
    Serial.print("Moving to v: ");
    Serial.print(v);
    Serial.print(" ,h: ");
    Serial.println(h);
    
    moveSlowly(h,v);

    Serial.print("... moved");
    
    int randDelay = random(1000, 5000);
    Serial.print(", dithering randomly for ");
    Serial.print(randDelay);
    Serial.println(" msec");
    dither(randDelay);
  }
}

void startServos(){
    hservo.attach(hServoPIN);
    vservo.attach(vServoPIN);
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

void pushButton(){
  if(state == idle){
    state = starting;
  }
  if(state == inUse){
    state = stopping;
  }
}


