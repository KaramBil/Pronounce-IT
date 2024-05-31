<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $email = $_GET['email'];


 $stmt = $bdd->prepare("UPDATE `users` SET `etat` = '0' WHERE `users`.`email` = ?; ");
        $stmt->bind_param("s",$email);
		$stmt->execute();

 $stmt = $bdd->prepare("UPDATE `users` SET `invited` = '0' WHERE `users`.`sender` = ?; ");
        $stmt->bind_param("s",$email);
		$stmt->execute();

		 $stmt = $bdd->prepare("UPDATE `users` SET `categ` = 0 WHERE `users`.`sender` = ?; ");
        $stmt->bind_param("s",$email);
		$stmt->execute();

	 $stmt = $bdd->prepare("UPDATE `users` SET `sender` = NULL WHERE `users`.`sender` = ?; ");
        $stmt->bind_param("s",$email);
		$stmt->execute();

	


		
	   
	

?>