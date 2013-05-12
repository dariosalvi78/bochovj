
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
