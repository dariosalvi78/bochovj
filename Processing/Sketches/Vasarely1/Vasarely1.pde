
float variation = 0;
int direction = 1;
float VARMAX = 100;

void setup() {  // this is run once.   
    background(255);
    size(800, 600); 
    smooth();
    
    frameRate(30);
} 

void draw() { 
     background(255);
     
    int ellipseWidth = 100;
    int ellipseHeight = 100;
    
    fill(0);
    for(int i=ellipseWidth/2; i<width; i+=ellipseWidth){
        for(int j=ellipseHeight /2; j<height; j+=ellipseHeight ){
          float ewi;
          float ehi;
          float var = map(variation, 0, VARMAX, 0, 0.7);
          if(i<=width/2)
           ewi = map(i, 0, width/2, 0, 1) * var;
          else
           ewi = map(i, width/2, width, 1, 0) * var;
           
           if(j<=height/2)
           ehi = map(j, 0, height/2, 0, 1) * var;
          else
           ehi = map(j, height/2, height, 1, 0) * var;
          
          float ew = (1-ewi) * ellipseWidth;
          float eh = (1-ehi) * ellipseHeight;
          ellipse(i, j, ew, eh);
        }
    }
    if(direction == 1)
      variation ++;
    else
      variation --;
      
    if((variation >= VARMAX) || (variation ==0))
        direction = -direction;
}
