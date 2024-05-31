<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');

}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $Email = $_GET['email'];
		

$stmt = $bdd->prepare("UPDATE `users` SET `played` = played+1 WHERE email= ? ; ");
        $stmt->bind_param("s",$Email);
		$stmt->execute();

	



	   
	

?>


