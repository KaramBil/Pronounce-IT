<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $sender = $_GET['sender'];
       $receiver = $_GET['rec'];
       $categ =$_GET['categ'];

 $stmt = $bdd->prepare("UPDATE `users` SET `sender` = ? WHERE `users`.`email` = ?; ");
        $stmt->bind_param("ss",$sender,$receiver);
		$stmt->execute();

		$stmt = $bdd->prepare("UPDATE `users` SET `categ` = ? WHERE `users`.`email` = ?; ");
        $stmt->bind_param("ss",$categ,$receiver);
		$stmt->execute();
		
 $stmt = $bdd->prepare("UPDATE `users` SET `invited` = '1' WHERE `users`.`email` = ?; ");
        $stmt->bind_param("s",$receiver);
         $stmt->execute();




	


		
	   
	

?>