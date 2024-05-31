<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $Email = $_GET['Email'];
 $password = $_GET['password']; 
  $Avatar = $_GET['avatar']; 


 $stmt = $bdd->prepare("SELECT * FROM users where email =? ");
        $stmt->bind_param("s",$Email);
		$stmt->execute();

	


		$response = array();

      $donnees = $stmt->get_result()->fetch_assoc();
       $stmt->close();
	if($donnees==null){
      

		 $stmt1 = $bdd->prepare("INSERT INTO `users` (`id`, `email`, `password`, `avatar`) VALUES (NULL, ?, ? , ? ); ");
          
        $stmt1->bind_param("sss",$Email,$password,$Avatar);
		$stmt1->execute();
		$code="OK";
		array_push($response, array("code"=>$code));
	
}

       
	  
  if($donnees!==null){
  $code="NO";
		$message="EMAIL already exist";
		array_push($response, array("code"=>$code,"message"=>$message));
  }
 
	   
	         echo json_encode($response);

	   
	

?>