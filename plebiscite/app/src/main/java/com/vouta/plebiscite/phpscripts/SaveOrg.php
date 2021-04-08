<?php 
    if($_SERVER['REQUEST_METHOD'] == 'POST'){

        $name = $_POST['name'];



        $sql = "INSERT INTO ORGLIST (name) VALUES ($name)";

        require_once('connect.php');

        if(mysql_query($con,$sql)){
            echo 'Success';
        }else {
            echo 'failure';

        }

        mysql_close($con);
    }


?>