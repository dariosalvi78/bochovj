
void parseSerial(){
  
  int i=0;
  char commandbuffer[100];

  if(Serial.available()){
    delay(100);
    while( Serial.available() && i< 99) {
      commandbuffer[i++] = Serial.read();
      delay(100);
    }
  }

  if(i>0) {
    if(strcmp(commandbuffer, "start") ==0){
      state = starting;
    }
    if(strcmp(commandbuffer, "stop") ==0){
      state = stopping;
    }
  }
  memset(commandbuffer, 0, (sizeof(commandbuffer)/sizeof(commandbuffer[0])));

}

