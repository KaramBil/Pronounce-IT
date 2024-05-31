<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $receiver = $_GET['receiver'];
		$sender = $_GET['sender'];


 $stmt = $bdd->prepare("SELECT id FROM game where etat=1 and receiver =? and sender=? ");
        $stmt->bind_param("ss",$receiver,$sender);
		$stmt->execute();

	


		$response = array();

      $donnees = $stmt->get_result()->fetch_assoc();
       $stmt->close();
	
		array_push($response,$donnees);
	

       
	  
  
 
	   
	         echo json_encode($response);

	   
	

?>