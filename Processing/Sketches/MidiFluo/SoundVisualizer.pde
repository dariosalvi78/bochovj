class SoundVisualizer
{
  Minim minim;
  AudioInput input;
  FFT fft;
  int buffSize = 512;
  int FFTsize = 128;
  int step;
  
  int[] fftArray = new int[buffSize];
  
  public SoundVisualizer(Minim minim)
  {
    minim = minim;
    input = minim.getLineIn(Minim.STEREO, buffSize);
    fft = new FFT(buffSize, 44100);
    fft.window(FFT.HAMMING);
    fft.linAverages(FFTsize);
    step = round( (float)width/(float)fft.avgSize());
    println("Step: "+step);
  }
  
  void draw()
  {
    stroke(255);
    for(int i = 0; i < input.bufferSize() - 1; i++)
    { 
     line(i, 150 + input.right.get(i)*50, i+1, 150 + input.right.get(i+1)*50);
    }
    fft.forward(input.left);
    float max = 0;
    int imax = 0;
    for (int i = 0; i < fft.avgSize(); i++)
    {
      if(i>0)
        if(fft.getAvg(i) > max)
        {
          imax = i;
          max = fft.getAvg(i);
        }
      rect(i*step, height - (fft.getAvg(i)*height), step,  (fft.getAvg(i)*height));
    }
    PFont font = loadFont("CourierNew36.vlw"); 
    textFont(font); 
    text("Max: "+imax, 15, 30); 
  }
  
  void stop()
  {
    input.close();
    minim.stop();
  }
}
