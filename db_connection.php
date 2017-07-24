<?php
require_once "conf.php";

if ($_GET["card_num"]){
  $num = $_GET["card_num"];
  $con = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);

  if ($con->connect_error) {
      die("Connection failed: " . $conn->connect_error);
  }
  $sql ="SELECT card_pts FROM cards WHERE card_num=$num";
  $resArr= $con->query($sql)->fetch_array();
    #print_r($resArr);
    echo $resArr[0];
    #echo $resArr["card_pts"];
    #echo "Connected successfully";
    $con->close();
    #echo "Disconnected successfully";
} else {
  echo "Didn't get card_num";
}

 ?>
