<?php

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

 $Email = $_GET['Email'];
 $password = $_GET['password'];

        $stmt = $bdd->prepare("SELECT * FROM users where email =? and Password =?  ");
        $stmt->bind_param("ss",$Email,$password);
		$stmt->execute();

       
	  
      $response = array();

      $donnees = $stmt->get_result()->fetch_assoc();
       $stmt->close();
	   
	   
	   
	

if($donnees!==null){
      
		
			$code="OK";
		$message="OK";
		array_push($response, array("code"=>$code,"message"=>$message));



       echo json_encode($response);

	
	
}
else{

		$code="login failed..";
		$message="Incorrect user/pass";
		array_push($response, array("code"=>$code,"message"=>$message));
		echo json_encode($response);

}
?>