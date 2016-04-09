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

    if (isset($_POST['btn_acceder'])) {
        $telefono = $db->sec($_POST['telefono']);
        $contrasena = $db->sec($_POST['contrasena']);

        if ($telefono == null || $contrasena == null) {
            notificacion($msj = "Por favor complete todos los campos");
            header("Location: ../");
        } else {
                $consulta = "SELECT * FROM usuario WHERE contrasena = '" . base64_encode($contrasena) . "' AND telefono = '" . $telefono . "';";
                $db->execute($consulta);

                if ($result->num_rows == 1) {

                    while ($resultado = $result->fetch_assoc()) {
                        session_start();
                            $_SESSION["Id"] = $resultado['id_usuario'];
                            $_SESSION["Nombre"] = base64_decode($resultado['nombre']);
                            $_SESSION["Correo"] = base64_decode($resultado['correo']);
                            $_SESSION["Llave"] = $resultado['claveApi'];
                            $_SESSION["Telefono"] = $resultado['telefono'];
                        session_write_close();
                    }

                    header("Location:../user");

                } else {
                    notificacion($msj = "El usuario o contraseña son incorrectos, vuelve a intentar.");
                    header("Location: ../");
                }

            }
    }
?>