<?php
    error_reporting(0);
    require_once '../class/sesion.php';
    include_once '../class/Funciones.php';
    session_start();
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
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
</head>
<body>

    <nav class="navbar navbar-default navbar-fixed-top" role="banner" style="background-color:#F8CE5E;">
        <div class="container">
                <img src="../img/HurryPrintLogo.png" height="50" width="150" >
        </div>
    </nav>


    <div class="container" style="text-align:center;">
        <div class="row">
            <br><br><br>

            <div class="col-md-12" id="page_recarga">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Selecciona Sucursal</h2>
                        <hr> 
                        
                        <div class="well">

                            <?php
                                echo SeleccionarSucursales();
                            ?>        

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

    </script>

</body>
</html>