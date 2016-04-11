<?php

include('Connector.php');
include('GuardarSucursal.php');
include('GuardarClientes.php');

error_reporting(0);
session_start();

$ID = $_POST['ID_pantalla']; 

if ($ID == "01") {
    $nombre = $_POST['nombre'];
    $direccion = $_POST['direccion'];

    $almacenar = new GuardarSucursal($nombre, $direccion);
    echo $almacenar->insert_sucursal();

}

if ($ID == "02") {
        $nombre = $_POST['nombre'];
        $correo = $_POST['correo'];
        $contrasena = $_POST['contrasena'];

        $almacenar = new GuardarClientes($nombre, $correo, $contrasena);
        echo $almacenar->insert_clientes();
    }

?>