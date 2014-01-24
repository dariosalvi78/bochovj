#include <NRF24.h>
#include <SPI.h>


// Singleton instance of the radio
NRF24 nrf24(9, 10);

//#define ROLE_LED

#ifdef ROLE_LED
//LED pins
int pinB = 3;
int pinG = 5;
int pinR = 6;
#else
boolean readSensors = false;
const int POTR = A0;
const int POTG = A1;
const int POTB = A2;
#endif


//Packet
uint8_t RGB[3];



#ifdef ROLE_LED
void setRGBLED(int r, int g, int b){
  if(r==0){
    digitalWrite(pinR, HIGH);
  }
  else{
    analogWrite(pinR, 255-r);
  }
  if(g==0){
    digitalWrite(pinG, HIGH);
  }
  else{
    analogWrite(pinG, 255-g);
  }
  if(b==0){
    digitalWrite(pinB, HIGH);
  }
  else{
    analogWrite(pinB, 255-b);
  }
}
#endif

#ifndef ROLE_LED
void send(){
  if (!nrf24.send(RGB, 3))
    Serial.println("send failed");
  if (!nrf24.waitPacketSent())
    Serial.print("Could not send: ");
  else
    Serial.print("Sent: ");

  Serial.print(RGB[0]);
  Serial.print(" ");
  Serial.print(RGB[1]);
  Serial.print(" ");
  Serial.println(RGB[2]);
}
#endif


void setup()
{
  Serial.begin(57600);
  Serial.println("NRF24 RGB LED remote");
  Serial.print("ROLE: ");
#ifdef ROLE_LED
  Serial.println("LED");
#else
  Serial.println("Remote Control");
  Serial.println("Control mode: read values from serial");
  Serial.println("Sensor mode: read values from analog inputs");
  Serial.println("Press s to switch to sensor mode, anything else to quit");
#endif

#ifdef ROLE_LED
  //Turn LED OFF
  setRGBLED(0, 0, 0);
#endif

  if (!nrf24.init())
    Serial.println("NRF24 init failed");

  if (!nrf24.setChannel(1))
    Serial.println("setChannel failed");
#ifdef ROLE_LED
  if (!nrf24.setThisAddress((uint8_t*)"clie1", 5))
    Serial.println("setThisAddress failed");
#else
  if (!nrf24.setThisAddress((uint8_t*)"serv1", 5))
    Serial.println("setThisAddress failed");
  if (!nrf24.setTransmitAddress((uint8_t*)"clie1", 5))
    Serial.println("setTransmitAddress failed");
#endif
  if (!nrf24.setPayloadSize(3))
    Serial.println("setPayloadSize failed");
  if (!nrf24.setRF(NRF24::NRF24DataRate2Mbps, NRF24::NRF24TransmitPower0dBm))
    Serial.println("setRF failed");
  Serial.println("NRF24 initialised");
}


void loop()
{

#ifdef ROLE_LED
  //Receive 3 values and set the color

  Serial.println("waiting");
  nrf24.waitAvailable();
  uint8_t len = 3;
  if (!nrf24.recv(RGB, &len))
    Serial.println("read failed");

  Serial.print("Got data: ");
  Serial.print(RGB[0]);
  Serial.print(" ");
  Serial.print(RGB[1]);
  Serial.print(" ");
  Serial.println(RGB[2]);
  //Set the color
  setRGBLED(RGB[0], RGB[1], RGB[2]);
#else

  //Read from serial and send
  if(Serial.available()){
    String s = Serial.readStringUntil('\n');
    Serial.println(s.length());
    if(s.length() == 1){
      if(s.startsWith("s")){
        readSensors = true;
        Serial.println("Set to sensor mode");
      }
      else{
        readSensors = false;
        Serial.println("Set to command mode");
      }
    }
    else if(s.length() == 11){
      RGB[0]= s.substring(0,3).toInt();
      RGB[1]= s.substring(4,7).toInt();
      RGB[2]= s.substring(8,11).toInt();
      send();
    }
  }
  if(readSensors){
    boolean changed = false;
    int potr = map(analogRead(POTR), 0, 1024, 0, 255);
    if(potr != RGB[0]){
      RGB[0] = potr;
      changed = true;
    }
    int potg = map(analogRead(POTG), 0, 1024, 0, 255);
    if(potg != RGB[1]){
      RGB[1] = potg;
      changed = true;
    }
    int potb = map(analogRead(POTB), 0, 1024, 0, 255);
    if(potb != RGB[2]){
      RGB[2] = potb;
      changed = true;
    }
    if(changed)
      send();
  }
#endif
}




