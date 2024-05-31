<?php
$json_in = file_get_contents('php://input');
$in = json_decode($json_in);

try
{
$bdd = new mysqli('localhost', 'root', '','tutorial2');


}catch (Exception $e){die('Erreur : ' . $e->getMessage());}

       
		$idgame=$_GET['idgame'];
     $a=$_GET['a'];
     $b=$_GET['b'];
       $c=$_GET['c'];
       $d=$_GET['d'];
       $e=$_GET['e'];
       $f=$_GET['f'];
       $g=$_GET['g'];


 $stmt = $bdd->prepare("UPDATE `game` SET `img1` = ? WHERE `game`.`id` = ?  ; ");
        $stmt->bind_param("ss",$a,$idgame);
		$stmt->execute();

     $stmt = $bdd->prepare("UPDATE `game` SET `img2` = ? WHERE `game`.`id` = ?  ; ");
        $stmt->bind_param("ss",$b,$idgame);
    $stmt->execute();

     $stmt = $bdd->prepare("UPDATE `game` SET `img3` = ? WHERE `game`.`id` = ?  ; ");
        $stmt->bind_param("ss",$c,$idgame);
    $stmt->execute();

     $stmt = $bdd->prepare("UPDATE `game` SET `img4` = ? WHERE `game`.`id` = ?  ; ");
        $stmt->bind_param("ss",$d,$idgame);
    $stmt->execute();

     $stmt = $bdd->prepare("UPDATE `game` SET `img5` = ? WHERE `game`.`id` = ?  ; ");
        $stmt->bind_param("ss",$e,$idgame);
    $stmt->execute();

     $stmt = $bdd->prepare("UPDATE `game` SET `img6` = ? WHERE `game`.`id` = ?  ; ");
        $stmt->bind_param("ss",$f,$idgame);
    $stmt->execute();

     $stmt = $bdd->prepare("UPDATE `game` SET `img7` = ? WHERE `game`.`id` = ?  ; ");
        $stmt->bind_param("ss",$g,$idgame);
    $stmt->execute();

	


	   
	

?>