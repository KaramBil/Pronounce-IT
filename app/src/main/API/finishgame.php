<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

      	$idgame=$_GET['idgame'];

 $stmt = $bdd->prepare("UPDATE `game` SET `etat` = 0 WHERE id= ? ; ");
        $stmt->bind_param("s",$idgame);
		$stmt->execute();


	  
	

?>