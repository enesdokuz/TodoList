<?php
require "init.php";


if(isset($_POST["email"]) && isset($_POST["password"]) ){

    $email = $_POST["email"];
    $pass = md5($_POST["password"]);

    $sql_login = "SELECT id FROM users WHERE email like '$email' and password like '$pass' LIMIT 1";
    $result = $conn->query($sql_login);
    if(mysqli_num_rows($result)>0){

        $data = array("user_id" => mysqli_fetch_object($result)->id);
        $json = json_encode($data);
        echo $json;
        http_response_code(202);
    }else{

        http_response_code(401);
    }
}else{

    http_response_code(405);
}

?>