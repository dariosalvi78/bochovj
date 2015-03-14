# Setup instructions #

## Download Eclipse ##

First of all download Eclipse:  http://www.eclipse.org/downloads/
The SE version will do fine (Eclipse IDE for Java Developers).

## Donwload a SVN client ##

Eclipse has one by default, you can find it in the plugins, just look for SVN in Help->Install new software, then choose your's Eclipse version repository (e.g. Galileo).
Or you can use an external client. I usually prefer this option.
One of the most used on is [[Tortoise SVN ](http://tortoisesvn.tigris.org/)].



## Download further libraries ##
JMF is used for capturing pictures from a webcam: http://java.sun.com/javase/technologies/desktop/media/jmf/2.1.1/download.html


## Checkout the code from SVN ##

Follow the instructions given here:
http://code.google.com/p/bochovj/source/checkout
Switch the Eclipse workspace to where you've downloaded the code / java.

## Create a classpath variable ##

Create a java classpath variable: Window->Preferences->Java->BuildPath->ClassPath variables->New. Name it "Libraries" (without quotes) and select Folder. Point it to the Libraries subdirectory in the java directory.

## Fix the Acces restriciton problem with JMF ##

If the Common project doesn't compile follow this suggestion:

http://lkamal.blogspot.com/2008/09/eclipse-access-restriction-on-library.html


## Compile and run ##

Import the existing projects and compile (should compile automatically).
Select the project you want to run and the class that contains a main method and run!


Everything's fine?
If not please contact me.