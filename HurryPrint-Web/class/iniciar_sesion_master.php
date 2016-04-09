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
        $correo = $db->sec($_POST['correo']);
        $contrasena = $db->sec($_POST['contrasena']);

        if ($correo == null || $contrasena == null) {
            notificacion($msj = "Por favor complete todos los campos");
            header("Location: ../");
        } else {

                $consulta = "SELECT * FROM clientes WHERE correo = '" . $correo . "' AND contrasena = '" . $contrasena . "' AND master = '1';";
                $db->execute($consulta);

                if ($result->num_rows == 1) {

                    while ($resultado = $result->fetch_assoc()) {
                        session_start();
                        $_SESSION["Nombre"] = $resultado['nombre'];
                        $_SESSION["Id"] = $resultado['id_cliente'];
                        session_write_close();
                    }

                    header("Location: ../admin/master.php");

                } else {
                    notificacion($msj = "El usuario o contraseña son incorrectos, vuelve a intentar.");
                    header("Location: ../");
                }

            }
    }
?>