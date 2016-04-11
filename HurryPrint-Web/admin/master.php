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


    <div class="container">
        <div class="row">

            <!-- MENÚ IZQUIERDO -->
            <div class="col-md-3" id="leftCol">
                <br><br>
                  <ul class="nav nav-stacked" id="sidebar" style="background-color:#F8CE5E;">
                      <br><br>
                      <li><a href="#" id="recarga">Alta de Sucursal</a></li>
                      <li><a href="#" id="imprimir">Alta de Usuarios</a></li>                  
                      <li><a href="#" id="perfil">Estado de Cuenta</a></li>
                      <hr>                  
                      <li><a href="../class/cerrar_sesion.php" id="creditos">Cerrar</a></li>
                  </ul>
            </div>

            <br><br>

            <div class="col-md-7" id="page_recarga">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Alta de Sucursal</h2>
                        <hr> 
                        
                        <div class="well">

                            <div class="input-group">
                                <span class="input-group-addon">Nombre de Tienda</span>
                                <input type="text" id="nom_tienda" class="form-control" aria-describedby="basic-addon1">
                            </div><br>

                            <div class="input-group">
                                <span class="input-group-addon">Dirección</span>
                                <input type="text" id="direccion" class="form-control" aria-describedby="basic-addon1">
                            </div><br>

                            <div id="boton" style="text-align:right;">
                                <button class="btn btn-success" id="btn_sucursal" > LISTO </button>
                            </div>           

                        </div>

                        <br>
                        <h2>Sucursales Registradas</h2>
                        <hr>
                        <br>

                        <?php     
                            echo MostrarSucursales();     
                        ?>

                    </div>
                </div>
            </div>


            <div class="col-md-9" id="page_imprimir">
                
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Alta de Administradores</h2>
                        <hr>

                        <div class="well">
                            <div class="input-group">
                                <span class="input-group-addon">Nombre de Usuario</span>
                                <input type="text" id="nom_usuario" class="form-control" aria-describedby="basic-addon1">
                            </div><br>

                            <div class="input-group">
                                <span class="input-group-addon">Correo</span>
                                <input type="text" id="correo" class="form-control" aria-describedby="basic-addon1">
                            </div><br>

                            <div class="input-group">
                                <span class="input-group-addon">Contrasena</span>
                                <input type="text" id="contrasena" class="form-control" aria-describedby="basic-addon1">
                            </div><br>

                            <div id="boton" style="text-align:right;">
                                <button class="btn btn-success" id="btn_cliente" > LISTO </button>
                            </div>                       
                        </div>

                        <br>
                        <h2>Administradores Registrados</h2>
                        <hr>
                        <br>

                        <?php     
                            echo MostrarUsuarios();     
                        ?>

                    </div> 
                </div>

            </div>


            <div class="col-md-9" id="page_perfil">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Estado de Cuenta</h2>
                        <hr> 
                        
                        <div class="well">

                            <table class="table table-hover">
                                <thead >
                                    <tr>
                                        <th>Ventas</th>
                                        <th>Comisiones</th>
                                        <th>Ganancia Neta</th>
                                        <th>Mes</th>
                                    </tr>
                                </thead>

                                <tbody>                                    
                                    <?php echo EstadoCuenta($_SESSION["Id"]);  ?>
                                </tbody>
                            </table>         

                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>

    <!-- Modal Actualizar Usuario-->
    <div id="modalUpdate" class="modal fade" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Actualizar Usuario</h4>
                </div>
                  
                    <div id="actualizarUsuario">
                    </div> 

            </div>

      </div>
    </div>

    <!-- Modal Actualizar Sucursal -->
    <div id="modalUpdateSucursal" class="modal fade" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Actualizar Sucursal</h4>
                </div>
                  
                    <div id="actualizarSucursal">
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

        function formUsuario(idUsuario) {
            document.getElementById("actualizarUsuario").innerHTML =

                '<div class="well">' +
                        '<form action="../class/ActualizarInfoUsuario.php?id=' + idUsuario + '"  method="POST" enctype="multipart/form-data">' +

                            '<div class="input-group">' +
                                '<span class="input-group-addon">Nombre de Usuario</span>' +
                                '<input type="text" id="nom_usuario" name="nom_usuario" class="form-control" aria-describedby="basic-addon1">' +
                            '</div><br>' +

                            '<div class="input-group">' +
                                '<span class="input-group-addon">Correo</span>' +
                                '<input type="text" id="correo" name="correo" class="form-control" aria-describedby="basic-addon1">' +
                            '</div><br>' +

                            '<div class="input-group">' +
                                '<span class="input-group-addon">Contrasena</span>' +
                                '<input type="text" id="contrasena" name="contrasena" class="form-control" aria-describedby="basic-addon1">' +
                            '</div><br>' +

                            '<div id="boton" style="text-align:right;">' +
                                '<input type="submit" class="btn btn-success" id="actualizar" name="actualizar" value="LISTO"/>' +
                            '</div>' +

                        '</form>' +
                    '</div>';
        }

        function formSucursal(idSucursal) {
            document.getElementById("actualizarSucursal").innerHTML =

                '<div class="well">' +
                        '<form action="../class/ActualizarInfoTienda.php?id=' + idSucursal + '"  method="POST" enctype="multipart/form-data">' +

                            '<div class="input-group">' +
                                '<span class="input-group-addon">Nombre de Tienda</span>' +
                                '<input type="text" id="nom_tienda" name="nom_tienda"class="form-control" aria-describedby="basic-addon1">' +
                            '</div><br>' +

                            '<div class="input-group">' +
                                '<span class="input-group-addon">Dirección</span>' +
                                '<input type="text" id="direccion" name="direccion"class="form-control" aria-describedby="basic-addon1">' +
                            '</div><br>' +

                            '<div id="boton" style="text-align:right;">' +
                                '<input type="submit" class="btn btn-success" id="actualizar" name="actualizar" value="LISTO"/>' +
                            '</div>' +

                        '</form>' +
                    '</div>';
        }

        function eliminarUsuario(idUsuario){

            if (confirm("¿Eliminar Usuario?" ) == true) {
                window.location = '../class/eliminarUsuario.php?id=' + idUsuario;
            } else {
                return false;
            }            
        }

        function eliminarSucursal(idSucursal){

            if (confirm("¿Eliminar Sucursal?" ) == true) {
                window.location = '../class/eliminarSucursal.php?id=' + idSucursal;
            } else {
                return false;
            }            
        }

    </script>

</body>
</html>
