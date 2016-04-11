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
                            echo    InfoSucursales($_GET["id"]);
                        ?>
                    </h1>

                    <hr>

                </div>
            </div>

            <div class="col-md-12" id="page_recarga" style="text-align:center;">
                 <br>
                        
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Documentos Entregados</h2>  
                         <h4> <strong>Actualizado: </strong><?php echo ObtenerFechaHoy();?><h4>       
                        <hr>      
                            <div class="well"> 
                                <table class="table table-hover"> 
                                    <thead  >
                                        <tr>
                                                <th style="text-align:center">Folio</th>
                                                <th style="text-align:center">Nombre</th>                                             
                                                <th style="text-align:center">No. Hojas</th>
                                                <th style="text-align:center">Rango de Hojas</th>
                                                <th style="text-align:center">Blanco y Negro</th>                                                       
                                                <th style="text-align:center">Color</th>                                                        
                                                <th style="text-align:center">Carátula a Color</th>                                                     
                                                <th style="text-align:center">Ambos Lados</th>                                                      
                                                <th style="text-align:center">Carta</th>                                                        
                                                <th style="text-align:center">Oficio</th>     
                                        </tr>
                                    </thead>
                                    <?php     echo EntregadosImpresion($_GET["id"])      ?>
                                </table>
                            </div>
                    </div>
                </div>
            </div>



            <div class="col-md-9" id="page_imprimir" style="text-align:center;">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Recarga HurryApp</h2>
                        <hr>

                        <div class="well">

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