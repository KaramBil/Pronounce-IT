
<?php

	


	// Create connection
	$conn = mysqli_connect("localhost", "root", "","tutorial2");
	// Check connection
	if (!$conn) {
	    die("Connection failed: " . mysqli_connect_error());
	}



	$sql = ("SELECT * FROM users where etat =1 ");
	$result = mysqli_query($conn, $sql);

	while($row = mysqli_fetch_assoc($result)) {
	    $output[]= $row;
	}

	if (!empty($output)) {
		echo json_encode($output);
	}else{
		echo "no rows";
	}
echo json_encode($output);
	$conn->close();


?>


