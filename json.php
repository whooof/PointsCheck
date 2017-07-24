<?php
require_once "conf.php";

if ($_GET["card_num"]){
  $num = $_GET["card_num"];
  $con = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);

  if ($con->connect_error) {
      die("Connection failed: " . $conn->connect_error);
  }
  $sql ="SELECT * FROM cards";
  $result = $con->query($sql);
  $movieNameArray = array();
  while ($rowFilms = $result->fetch_all()) {
  $movieNameArray = $rowFilms;
  }
  print_r($movieNameArray);
    echo "<br>";
    echo json_encode($movieNameArray);
    #echo $resArr["card_pts"];
    #echo "Connected successfully";
    $con->close();
    $result->close();
    #echo "Disconnected successfully";
} else {
  echo "Didn't get card_num";
}

 ?>
