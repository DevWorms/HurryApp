<?php
    error_reporting(0);

    include('Connector.php');
    $db = new Connector();

    function notificacion($msj) {
        echo 
        "<script>
            alert('" . $msj . "');
        </script>";
    }

    if (isset($_POST['btn_registrarse'])) {
        $numero = $db->sec($_POST['numero']);
        $contrasena = $db->sec($_POST['contrasena']);

        if ($numero == null || $contrasena == null) {
            notificacion($msj = "Por favor complete todos los campos");
            header("Location: ../");
        } else {

                $apikey = md5(microtime() . rand());
                $Id = "";

                $consulta = "INSERT INTO usuario(telefono, contrasena, claveApi)
                    VALUES ('".$numero."','".base64_encode($contrasena)."','".$apikey."')";
                $db->execute($consulta);

                $consulta2 = "SELECT * FROM usuario WHERE claveApi = '" . $apikey . "' AND contrasena = '" . base64_encode($contrasena) . "';";
                $db->execute($consulta2);

                    if ($result->num_rows == 1) {

                        while ($resultado = $result->fetch_assoc()) {
                            $Id = $resultado['id_usuario'];
                        }

                    } 

                $consulta3 = "INSERT INTO wallet(id_usuario)  VALUES ('".$Id."')";
                $db->execute($consulta3);

                notificacion($sms="Registro con éxito!");
                echo "<script>
                   window.location = '../admin/admin.php'
               </script>";  
            }
    }
?>