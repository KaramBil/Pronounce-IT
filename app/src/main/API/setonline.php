<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $email = $_GET['email'];
       $langue = $_GET['langue'];


 $stmt = $bdd->prepare("UPDATE `users` SET `etat` = '1' WHERE `users`.`email` = ?; ");
        $stmt->bind_param("s",$email);
		$stmt->execute();

		 $stmt = $bdd->prepare("UPDATE `users` SET `langue` = ? WHERE `users`.`email` = ?; ");
        $stmt->bind_param("ss",$langue,$email);
		$stmt->execute();

	


		
	   
	

?>