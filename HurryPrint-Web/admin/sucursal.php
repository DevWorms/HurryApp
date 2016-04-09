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
                      <li><a href="#" id="recarga">Cola de Impresión</a></li>
                      <li><a href="#" id="imprimir">Recarga HurryApp</a></li> 
                      <li><a href="#" id="registro">Registrar Usuario</a></li>     
                      <li><a href="admin.php" id="imprimir">Regresar a Sucursales</a></li>
                      <hr>
                      <li><a href="entregados.php?id=<?php echo $_GET["id_sucursal"] ?>" id="creditos">Mostrar Entregados</a></li>
                      <li><a href="../class/cerrar_sesion.php" id="creditos">Cerrar</a></li>
                  </ul>
            </div>

            <br><br>

            <div class="col-md-5" id="Informacion" style="text-align:center;">
                <div class="well"> 
                    <h1>
                      <?php
                            echo    InfoSucursales($_GET["id_sucursal"]);
                        ?>
                    </h1>

                    <hr>

                </div>
            </div>

            <div class="col-md-3" id="Status">
                <div class="well"> 
                    <form action="../class/ActualizarStatusTienda.php?id=<?php echo  $_GET["id_sucursal"];  ?>" method="POST" enctype="multipart/form-data">
                        <label>

                          <?php
                                echo    SucursalStatus($_GET["id_sucursal"]);
                            ?>

                        </label>
                        <br><br>
                        <input type="submit" class="btn btn-info" id="status" name="status" value="¡Actualiza status de tienda!"/>
                    </form>
                </div>
            </div>

            <div class="col-md-12" id="page_recarga" style="text-align:center;">
                 <br>
                        
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Cola de Impresión</h2>  
                         <h4> <strong>Actualizado: </strong><?php echo ObtenerFechaHoy();?><h4>       
                        <hr>      

                        <form action="../class/ActualizarCola.php?id=<?php echo  $_GET["id_sucursal"];  ?>" method="POST" enctype="multipart/form-data">

                            <input type="submit" class="btn btn-success" id="subir" name="subir" value="¡Actualizar Cola de Impresión!"/>
                            <br><br>
                            <div class="well"> 
                                <table class="table table-hover"> 
                                    <thead  >
                                        <tr>
                                                <th style="text-align:center">Folio</th>
                                                <th style="text-align:center">Nombre</th>
                                                <th style="text-align:center">Ver</th>                                                      
                                                <th style="text-align:center">No. Hojas</th>
                                                <th style="text-align:center">Rango de Hojas</th>
                                                <th style="text-align:center">Blanco y Negro</th>                                                       
                                                <th style="text-align:center">Color</th>                                                        
                                                <th style="text-align:center">Carátula a Color</th>                                                     
                                                <th style="text-align:center">Ambos Lados</th>                                                      
                                                <th style="text-align:center">Carta</th>                                                        
                                                <th style="text-align:center">Oficio</th>        
                                                <th style="text-align:center">Status</th>
                                                <th style="text-align:center">Entregado</th>
                                        </tr>
                                    </thead>
                                    <?php     echo ColaImpresion($_GET["id_sucursal"])      ?>
                                </table>
                            </div>
                        </form>
                    </div>
                </div>
            </div>



            <div class="col-md-9" id="page_imprimir" style="text-align:center;">
                 <br>

                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Recarga HurryApp</h2>
                        <hr>

                        <div class="well" style="text-align:left;">
                            <form action="recarga.php?id=<?php echo  $_GET["id_sucursal"];  ?>" method="POST" enctype="multipart/form-data">
                                
                                <label for="telefonotxt">Consulta por teléfono</label>
                                <div class="input-group">
                                  <span class="input-group-addon" id="basic-addon3"> Como aparece en la app del usuario, sin espacios</span>
                                  <input type="text" class="form-control" id="telefonotxt" name="telefonotxt" aria-describedby="basic-addon3" placeholder="5512345678" required>
                                </div>
                                <div style="text-align:right;">
                                    <br>
                                    <input type="submit" class="btn btn-success" id="consultaBtn" name="consultaBtn" value="Consultar usuario"/> 
                                </div>                               
                            </form>

                        </div>

                    </div> 
                </div>

            </div><div class="col-md-7" id="page_registro" style="text-align:center;">
                 <br>

                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Registrar nuevo usuario</h2>
                        <hr>

                        <div class="well" style="text-align:left;">
                            <form action="../class/registrarse.php" method="POST" enctype="multipart/form-data">
                                     
                                <div class="input-group">
                                    <span class="input-group-addon">Número de teléfono</span>
                                    <input type="text" id="numero" name="numero" class="form-control" aria-describedby="basic-addon1">
                                </div><br>

                                <div class="input-group">
                                    <span class="input-group-addon">Contraseña</span>
                                    <input type="text" id="contrasena" name="contrasena" class="form-control" aria-describedby="basic-addon1">
                                </div>

                                <div style="text-align:right;">
                                    <br>
                                    <input type="submit" class="btn btn-success" id="btn_registrarse" name="btn_registrarse" value="Registrar usuario"/> 
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
        $("#page_registro").hide();

        $("#recarga").click(function(){
            $("#page_recarga").show();
            $("#page_imprimir").hide();
            $("#page_perfil").hide();            
            $("#page_historial").hide();    
            $("#page_registro").hide();
        });

        $("#imprimir").click(function(){
            $("#page_imprimir").show();
            $("#page_recarga").hide();
            $("#page_perfil").hide();            
            $("#page_historial").hide();
            $("#page_registro").hide();
        });

        $("#perfil").click(function(){
            $("#page_perfil").show();
            $("#page_imprimir").hide();
            $("#page_recarga").hide();            
            $("#page_historial").hide();
            $("#page_registro").hide();
        });

        $("#historial").click(function(){
            $("#page_historial").show();
            $("#page_imprimir").hide();
            $("#page_perfil").hide();            
            $("#page_recarga").hide();
            $("#page_registro").hide();
        });

        $("#registro").click(function(){
            $("#page_historial").hide();
            $("#page_imprimir").hide();
            $("#page_perfil").hide();            
            $("#page_recarga").hide();
            $("#page_registro").show();
        });

    </script>

</body>
</html>