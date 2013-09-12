// LightBox: a controller for RGB strips
// Released under Artistic License 2: http://opensource.org/licenses/artistic-license-2.0
#include "LowPassFilter.c"

//Pins assignement
const int VALUE_POT = 3;
const int HUE_POT = 4;
const int SAT_POT = 1;
const int COL_OSC_PERIOD_POT = 0;
const int VAL_OSC_PERIOD_POT = 2;
const int MIC_PIN = 5;

const int VAL_OSC_SELECTOR_1 = 2;
const int VAL_OSC_SELECTOR_2 = 3;
const int VAL_OSC_SELECTOR_3 = 4;
const int VAL_OSC_SELECTOR_4 = 5;

const int COL_OSC_SELECTOR_1 = 6;
const int COL_OSC_SELECTOR_2 = 7;
const int COL_OSC_SELECTOR_3 = 8;
const int COL_OSC_SELECTOR_4 = 12;

const int R_PWM = 11;
const int G_PWM = 10;
const int B_PWM = 9;

//Type of oscillation
enum oscType {
 NONE, //No oscillation, use potentiometer
 TRIAN, //Triangle wave
 SQUARE, //Square wave
 RAND, //Random
 MIC //Microphone input
} ;

oscType valueOsc;
oscType colorOsc;

//Oscillation period of the value
unsigned int valOscPeriod;
//Counter used for oscillating
unsigned int valOscCounter;
//Maximum oscillation period
const int VAL_OSC_PERIOD_MAX = 50;
//Minimum oscillation period
const int VAL_OSC_PERIOD_MIN = 2;

unsigned int colorOscPeriod;
unsigned int colorOscCounter;
const int COL_OSC_PERIOD_MAX = 200;
const int COL_OSC_PERIOD_MIN = 10;

//Hue: 0=0°, 255=360°
unsigned int H;
//Saturation: 0=0, 255=1
unsigned int S;
//Value: 0=0, 255=1
unsigned int V;


//The number of samples to buffer before analyzing them
int samplesN = 150;

LowPassFilter* filter;

int iteration = 0;
int maxpeak = 0 ;
int minPeak = 1023;

void setup(){
  //Initialization of Random
  randomSeed(analogRead(0));
  
  //Initilization of pins
  pinMode(VAL_OSC_SELECTOR_1, INPUT);
  pinMode(VAL_OSC_SELECTOR_2, INPUT);
  pinMode(VAL_OSC_SELECTOR_3, INPUT);
  pinMode(COL_OSC_SELECTOR_1, INPUT);
  pinMode(COL_OSC_SELECTOR_2, INPUT);
  pinMode(COL_OSC_SELECTOR_3, INPUT);
  pinMode(R_PWM, OUTPUT);
  pinMode(G_PWM, OUTPUT);
  pinMode(B_PWM, OUTPUT);
  
  //Initialization of variables
  valueOsc = NONE;
  valOscPeriod = VAL_OSC_PERIOD_MAX;
  valOscCounter = 0;
  
  colorOsc = NONE;
  colorOscPeriod = COL_OSC_PERIOD_MAX;
  colorOscCounter = 0;
  
  H = 0;
  S = 0;
  V = 0;
  
  //Initialize filter
  filter = new LowPassFilter();
  LowPassFilter_init(filter);
  
  //Serial for debugging
  Serial.begin(115200);
}


void loop(){
  //Get value oscillation type
  readValueOsc();
  //Read potentiometer, the maximum value
  int Vmax = map(analogRead(VALUE_POT), 1023, 0, 0, 255);
  //Get Color (Saturation&Hue) oscillation type
  readColorOsc();
  //Read the potentiometer, the maximum saturation
  int Smax = map(analogRead(SAT_POT), 1023, 0, 0, 255);
  
  //Do the sound analysis
  int peak = 0;    
  //Get some samples
  for(int k=0; k<samplesN; k++){
   int val = analogRead(MIC_PIN);
   LowPassFilter_put(filter, val);//Filter the samples
   int filtered = LowPassFilter_get(filter);
   peak = max(peak, filtered);//Take the peak
   maxpeak = max(maxpeak, peak);
   minPeak = min(minPeak, peak);
   iteration++;
   if(iteration == 100){//Reset min and max peaks every 100 iterations
     iteration = 0;
     maxpeak = 0;
     minPeak = 1023;
   }
   peak = map(peak, minPeak, maxpeak, 0, Vmax);//At this point have the peak mapped to 0-255 interval
  }
  
  //Do the value:
  if(valueOsc == TRIAN){
    readValueOscPeriod();
    unsigned int halfPeriod = valOscPeriod/2;
    valOscCounter ++;
    if(valOscCounter >= valOscPeriod)
      valOscCounter = 0;
    if(valOscCounter <= halfPeriod)
      V = map(valOscCounter, 0, halfPeriod, 0, Vmax);
    else
      V = map(valOscPeriod-valOscCounter, 0, halfPeriod, 0, Vmax);
  }
  else if(valueOsc == SQUARE){
    readValueOscPeriod();
    unsigned int halfPeriod = valOscPeriod/2;
    valOscCounter ++;
    if(valOscCounter >= halfPeriod){
      valOscCounter = 0;
      if(V > 0) V = 0;
      else V = Vmax;
    }
  } 
  else if(valueOsc == RAND){
    readValueOscPeriod();
    unsigned int halfPeriod = valOscPeriod/2;
    valOscCounter ++;
    if(valOscCounter >= halfPeriod){
      valOscCounter = 0;
      V = random(Vmax+1);
    }
  } 
  else if(valueOsc == MIC) {
    V = peak;
  }
  else{ //Default is like NONE, use potentiometer
    V = Vmax;
  }
 
  //Do the color, Hue and Saturation
  if(colorOsc == TRIAN){
    readColorOscPeriod();
    colorOscCounter ++;
    if(colorOscCounter >= colorOscPeriod)
      colorOscCounter = 0;
    //hue variates through all the period
    H = map(colorOscCounter, 0, colorOscPeriod-1, 0, 255);
    //saturation variates in half the period
    S = map(colorOscCounter/2, 0, (colorOscPeriod-1)/2, 0, Smax);
  }
  else if(colorOsc == SQUARE){ //same as in triangular but with saturation always maximum
    readColorOscPeriod();
    colorOscCounter ++;
    if(colorOscCounter >= colorOscPeriod)
      colorOscCounter = 0;
    H = map(colorOscCounter, 0, colorOscPeriod-1, 0, 255);
    S = Smax;
  } 
  else if(colorOsc == RAND){
    readColorOscPeriod();
    unsigned int halfPeriod = colorOscPeriod/2;
    colorOscCounter ++;
    if(colorOscCounter >= halfPeriod){
      colorOscCounter = 0;
      H = random(256);
      S = random(Smax); 
    }
  } 
  else if(colorOsc == MIC){
    H = peak;
    S = Smax;
  } 
  else{ //Default is NONE, use potentiometers
    H = map(analogRead(HUE_POT), 0, 1023, 0, 255);
    S = Smax;
  } 
 
  //Convert to RGB
  //adapted from http://stackoverflow.com/questions/3018313/algorithm-to-convert-rgb-to-hsv-and-hsv-to-rgb
  byte r,g,b;
  unsigned int region, remainder, p, q, t;
  if (S == 0) { //grey
    r = V;
    g = V;
    b = V;
  } else {
     region = H / 43; //range: 0..5
     remainder = (H - (region * 43)) * 6; //range: 0..240
     p = (V * (255 - S)) >> 8;
     q = (V * (255 - ((S * remainder) >> 8))) >> 8;
     t = (V * (255 - ((S * (255 - remainder)) >> 8))) >> 8;
     switch (region) {
      case 0:
       r = V; g = t; b = p;
       break;
     case 1:
       r = q; g = V; b = p;
       break;
     case 2:
       r = p; g = V; b = t;
       break;
     case 3:
       r = p; g = q; b = V;
       break;
     case 4:
       r = t; g = p; b = V;
       break;
     default:
       r = V; g = p; b = q;
       break;
     }
  }
  //Write PWM
  analogWrite(R_PWM, r);
  analogWrite(G_PWM, g);
  analogWrite(B_PWM, b);
  
  //Print some debug information on the serial
  if(Serial.available()) {
    Serial.print(H);
    Serial.print(" ");
    Serial.print(S);
    Serial.print(" ");
    Serial.print(V);
  
    Serial.print(" - ");
    Serial.print(r);
    Serial.print(" ");
    Serial.print(g);
    Serial.print(" ");
    Serial.print(b);
  
    Serial.println();
  }
}

//Gest the type of oscillation for the value
void readValueOsc(){
  boolean v1 = digitalRead(VAL_OSC_SELECTOR_1);
  boolean v2 = digitalRead(VAL_OSC_SELECTOR_2);
  boolean v3 = digitalRead(VAL_OSC_SELECTOR_3);
  boolean v4 = digitalRead(VAL_OSC_SELECTOR_4);
  if(!v1 && !v2 && v3 && !v4){ //0 0 1 0
    valueOsc = TRIAN;
  } else if(!v1 && v2 && !v3 && !v4){ //0 1 0 0
    valueOsc = SQUARE;
  } else if(v1 && !v2 && !v3 && !v4){ //1 0 0 0
    valueOsc = RAND;
  } else if(!v1 && !v2 && !v3 && v4){ //0 0 0 1
    valueOsc = NONE;
  } else {
    valueOsc = MIC;
  }
}

//Gets the oscillation period from potentiometer
void readValueOscPeriod(){
  int val = analogRead(VAL_OSC_PERIOD_POT);
  valOscPeriod = map(val, 0, 1023, VAL_OSC_PERIOD_MIN, VAL_OSC_PERIOD_MAX);
}

//Gest the type of oscillation for the color
void readColorOsc(){
  boolean v1 = digitalRead(COL_OSC_SELECTOR_1);
  boolean v2 = digitalRead(COL_OSC_SELECTOR_2);
  boolean v3 = digitalRead(COL_OSC_SELECTOR_3);
  boolean v4 = digitalRead(COL_OSC_SELECTOR_4);
  if(v1 && !v2 && !v3 && !v4){ //1 0 0 0
    colorOsc = RAND;
  } else if(!v1 && v2 && !v3 && !v4){ //0 1 0 0
    colorOsc = SQUARE;
  } else if(!v1 && !v2 && v3 && !v4){ //0 0 1 0
    colorOsc = TRIAN;
  } else if(!v1 && !v2 && !v3 && v4){ //0 0 0 1
    colorOsc = NONE;
  } else {
    colorOsc = MIC;
  }
}

//Gets the oscillation period from potentiometer
void readColorOscPeriod(){
  int col = analogRead(COL_OSC_PERIOD_POT);
  colorOscPeriod = map(col, 0, 1023, COL_OSC_PERIOD_MIN, COL_OSC_PERIOD_MAX);
}
