<?php
$servername = "localhost";
$username = "root";
$password = "";

try {
    $conn = new PDO("mysql:host=$servername;dbname=tutorial2", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Connected successfully"; 
    
 //$stmt = $conn->prepare("SELECT email,password FROM users"); 
  //  $stmt->execute();


	}
catch(PDOException $e)
    {
    echo "Connection failed: " . $e->getMessage();
}

class User {
	
	
		private $db;
		
private $connection;
		
	

	function __construct() {
		
	$this -> db = new PDO("mysql:host=$servername;dbname=tutorial2", $username, $password);
	$this -> db ->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$this -> connection = $this->db=conn;
		}

		
	
	public function does_user_exist($email,$password)
		
{
			$query = "Select * from users where email='$email' and password = '$password' ";
			
$result = mysqli_query($this->connection, $query);
			if(mysqli_num_rows($result)>0){
				$json['success'] = ' Welcome '.$email;
				echo json_encode($json);
				mysqli_close($this -> connection);
			}else{
				$query = "insert into USERS (email, password) values ( '$email','$password')";
				$inserted = mysqli_query($this -> connection, $query);
				if($inserted == 1 ){
					$json['success'] = 'Acount created';
				}else{
					$json['error'] = 'Wrong password';
				}
				echo json_encode($json);
				mysqli_close($this->connection);
			}
			
		}
		
	}
	
	
	$user = new User();
	if(isset($_POST['email'],$_POST['password'])) {
		$email = $_POST['email'];
		$password = $_POST['password'];
		
		if(!empty($email) && !empty($password)){
			
			$encrypted_password = md5($password);
			$user-> does_user_exist($email,$password);
			
		}else{
			echo json_encode("you must type both inputs");
		}
		
	}

?>