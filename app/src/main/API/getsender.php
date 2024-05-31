<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $Email = $_GET['email'];


 $stmt = $bdd->prepare("SELECT * FROM users where email =? ");
        $stmt->bind_param("s",$Email);
		$stmt->execute();

	


		$response = array();

      $donnees = $stmt->get_result()->fetch_assoc();
       $stmt->close();
	
		array_push($response,$donnees);
	

 
	  
  
 
	   
	         echo json_encode($response);

	   
	

?>