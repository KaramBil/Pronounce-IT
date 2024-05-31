<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $Email = $_GET['email'];


 $stmt = $bdd->prepare("SELECT COUNT(*) from game where sender=? and etat=1");
        $stmt->bind_param("s",$Email);
		$stmt->execute();

	


		$response = array();

      $donnees = $stmt->get_result()->fetch_assoc();
       $stmt->close();
	
		array_push($response,$donnees);
	

       $stmt = $bdd->prepare("SELECT * from game where etat=1 and sender=?");
        $stmt->bind_param("s",$Email);
		$stmt->execute();

	


	

      $donnees1 = $stmt->get_result()->fetch_assoc();
       $stmt->close();
	
		array_push($response,$donnees1);
	
	  
  
 
	   
	         echo json_encode($response);

	   
	

?>