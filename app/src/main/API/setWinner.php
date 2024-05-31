<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $Email = $_GET['email'];
		
		$idgame=$_GET['id'];

$stmt = $bdd->prepare("UPDATE `game` SET `winner` = ? WHERE id= ? ; ");
        $stmt->bind_param("ss",$Email,$idgame);
		$stmt->execute();

	



	   
	

?>


