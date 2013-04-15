<?php

function myErrorHandler($errno, $errstr, $errfile, $errline)
{
   echo "<b>ERROR</b> [$errno] $errstr<br />\n";
   echo "  on line $errline in file $errfile";
   echo ", PHP " . PHP_VERSION . " (" . PHP_OS . ")<br />\n";
   return false;
}

ini_set("display_errors", 1);
error_reporting(-1);
set_error_handler("myErrorHandler");
include "php_serial.class.php";
?>

<!DOCTYPE html>
<html>
<body>

<h1>Cat's toy!</h1>

<form method="POST" action="">
<?php
if(isset($_POST['command']) && ($_POST['command'] == 'Start')){
  sendCommand("start");
  echo'Started !';
  echo'<input name="command" type="submit" value="Stop">';  
}
else{
  if(isset($_POST['command']) && ($_POST['command'] == 'Stop')){
    sendCommand("stop");
    echo'Stopped !';
  }
  echo'<input name="command" type="submit" value="Start">';
}
?>
</form>

</body>
</html>


<?php


function sendCommand($n){
$serial = new phpSerial;

$serial->deviceSet("/dev/ttyUSB0");

$serial->confBaudRate(9600);
$serial->confParity("none");
$serial->confCharacterLength(8);
$serial->confStopBits(1);
$serial->confFlowControl("none");


$serial->deviceOpen();


$serial->sendMessage($n);

// $read = $serial->readPort();


$serial->deviceClose();
}

?>
