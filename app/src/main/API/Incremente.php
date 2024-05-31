<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       $email = $_GET['email'];


$stmt = $bdd->prepare("SELECT COUNT(*) from game where sender=? and etat=0");
        $stmt->bind_param("s",$Email);
		$stmt->execute();

$row = mysqli_fetch_assoc($result)
		


	
				$inserted = mysqli_query($bdd, $query);
				if($inserted == 0 ){
					$json['success'] = 'Acount created';
				}else{
					$json['error'] = 'Wrong password';
				}
				echo json_encode($json);


		
	   
	

?>