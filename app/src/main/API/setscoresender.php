<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $Email = $_GET['email'];
		$score = $_GET['score'];
		$idgame=$_GET['idgame'];

 $stmt = $bdd->prepare("UPDATE `game` SET `scoresender` = ? WHERE sender=? and id= ? ; ");
        $stmt->bind_param("sss",$score,$Email,$idgame);
		$stmt->execute();


	   
	

?>