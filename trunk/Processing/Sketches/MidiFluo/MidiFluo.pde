import ddf.minim.signals.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;

MidiVisualizer MV;
Minim minim;
MidiManager MM;
SoundVisualizer SV;

void setup()
{
 size(400,400);
 background(0);
 MV = new MidiVisualizer();
 minim = new Minim(this);
 SV = new SoundVisualizer(minim);
 MM = new MidiManager(MV);
 MM.startDevice(); 
}

void draw()
{
  background(0);
  MV.draw();
  SV.draw();
}
