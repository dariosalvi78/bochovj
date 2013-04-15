#include <Servo.h> 

Servo hservo;
Servo vservo;

void setup() 
{ 
  Serial.begin(9600);
  hservo.attach(11);
  vservo.attach(10);

  hservo.write(0);
  vservo.write(0);
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
    commandbuffer[i++]='\0';
  }

  if(i>0) {
    Serial.print("Received line: ");
    Serial.println((char*)commandbuffer);
    char servo = commandbuffer[0];
    char angleBuffer[i-1]; 
    for(int j = 1; j< i; j++){
      angleBuffer[j-1] = commandbuffer[j];
    }

    int angle = atoi(angleBuffer);
    Serial.print("Angle: ");
    Serial.println(angle);

    if(servo == 'h'){
      Serial.println("Moving horizontally");
      hservo.write(angle);
    }

    if(servo == 'v'){
      Serial.println("Moving vertically");
      vservo.write(angle);
    }
  }
  memset(commandbuffer, 0, (sizeof(commandbuffer)/sizeof(commandbuffer[0])));
}








