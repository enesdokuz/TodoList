<?php
require "init.php";

if(isset($_POST["email"]) && isset($_POST["password"]) ){

    $email = $_POST["email"];
    $pass = md5($_POST["password"]);

    $sql_register = "INSERT INTO users (email, password) VALUES ('$email', '$pass') ";

    if(mysqli_query($conn, $sql_register)){
        $last_id = mysqli_insert_id($conn);
        $data = array("user_id" => $last_id);
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