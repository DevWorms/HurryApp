<?php

    error_reporting(0);
    require_once '../class/sesion.php';
    include_once '../class/Funciones.php';
    session_start();
    
    $telefono = $_POST['telefonotxt'];

    $db->execute("SELECT * FROM usuario WHERE telefono =" . $telefono . " ;");

    if ($result->num_rows == 0) {
        $respuesta = "No hay usuarios registrados.";
    } else {
        while ($resultado = $result->fetch_assoc()) {
            $respuesta = base64_decode($resultado["nombre"]) . "<br>" . "<h3>" . base64_decode($resultado["correo"]) . "</h3>";
            $id_usuario = $resultado["id_usuario"];
        }
    }

?>


<!DOCTYPE html>
<html lang="en">
<head>
  <link rel="shortcut icon" href="../img/HurryPrint.png" type="image/png" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>HurryPrint v1.0</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/styles.css" rel="stylesheet">   
    <link href="../font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>

    <!-- CAMBIAR COLOR AL VER DOCUMENTO -->
    <style>
        #ver:visited {
            color: green;
        }
            
        #ver:link {
            color: gray;
        }
    </style>


   </head>
<body>

    <nav class="navbar navbar-default navbar-fixed-top" role="banner" style="background-color:#F8CE5E;">
        <div class="container">
                <img src="../img/HurryPrintLogo.png" height="50" width="150" >
        </div>
    </nav>

    <div class="container">
        <div class="row">

            <!-- MENÚ IZQUIERDO -->
            <div class="col-md-3" id="leftCol">

                <br><br>

                  <ul class="nav nav-stacked" id="sidebar" style="background-color:#F8CE5E;">
                    <br>
                      <li><a href="sucursal.php?id_sucursal=<?php echo $_GET["id"] ?>" id="imprimir">Regresar a Cola de impresión</a></li>     
                      <br>
                  </ul>
            </div>

            <br><br>

            <div class="col-md-5" id="Informacion" style="text-align:center;">
                <div class="well"> 
                    <h1>
                      <?php
                            echo  $respuesta;
                            echo  $resultado["correo"];
                        ?>
                    </h1>

                    <hr>
                    <div class="panel-body">
                        <h2>Recarga</h2>     
                        <hr>      
                            <div class="well">                            
                                <form action="../class/recargaPrepago.php?id_usuario=<?php echo $id_usuario; ?>" method="POST" enctype="multipart/form-data" onsubmit="return validacion(<?php echo $_SESSION["Pass"] ?>)">                                 
                                    <select id="recarga" name="recarga" class="form-control">
                                        <option value= "5"> $5 pesos </option>
                                        <option value= "10"> $10 pesos </option>
                                        <option value= "20"> $20 pesos </option>
                                        <option value= "30"> $30 pesos </option>
                                        <option value= "50"> $50 pesos </option>
                                        <option value= "100"> $100 pesos </option>
                                    </select>
                                    <br>
                                    <div class="input-group">
                                      <label for="telefonotxt">Ingresa tu contraseña para confirmar</label>
                                      <input type="password" class="form-control" id="passAdmin" name="passAdmin" aria-describedby="basic-addon3" required>
                                    </div>
                                        <div>
                                        <br>
                                            <input type="submit" class="btn btn-warning" id="consultaBtn" name="consultaBtn" value="Recargar"/> 
                                        </div>
                                </form>
                            </div>
                    </div>

                </div>
            </div>

        </div>
    </div>

    <script src="../js/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/postform.js"></script>

    <!--
    
        SCRIPTING MENU
    
    -->

    <script>

        $("#page_recarga").show();
        $("#page_imprimir").hide();
        $("#page_perfil").hide();            
        $("#page_historial").hide();

        $("#recarga").click(function(){
            $("#page_recarga").show();
            $("#page_imprimir").hide();
            $("#page_perfil").hide();            
            $("#page_historial").hide();
        });

        $("#imprimir").click(function(){
            $("#page_imprimir").show();
            $("#page_recarga").hide();
            $("#page_perfil").hide();            
            $("#page_historial").hide();
        });

        $("#perfil").click(function(){
            $("#page_perfil").show();
            $("#page_imprimir").hide();
            $("#page_recarga").hide();            
            $("#page_historial").hide();
        });

        $("#historial").click(function(){
            $("#page_historial").show();
            $("#page_imprimir").hide();
            $("#page_perfil").hide();            
            $("#page_recarga").hide();
        });


        function validacion(pass)   {

            //      VALIDACIONES DE SALDO

            if(document.getElementById("passAdmin").value != pass ) {   
                alert ("Tu password es erróneo");
                    return false;
            }
            
            if (confirm("¿Confirmar recarga?" ) == true) {
                return true;
            } else {
                return false;
            }            
        }


    </script>

</body>
</html>