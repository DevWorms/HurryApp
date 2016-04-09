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


                <br><br><br><br>
    <!--main-->
    <div class="container" id="main">
        <div class="panel panel-default">
            <div class="panel-body">
                <!-- **********************  FORM 1  **********************-->
                <div class="well" style="text-align:center">
                    <h2>Accede!</h2>
                    <br><br>                   

                    <!--<button class="btn btn-success" id="btn_form1"> LogIn </button>-->
                    <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#ModalControl">Control</button>

                    <!--<button class="btn btn-success" id="btn_form1"> LogIn </button>-->
                    <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#ModalAdministrador">Sucursales</button>

                </div> 
                
                </div>
        </div>

        <!-- **********************  FOOTER  ********************** -->
        <div class="row">
            <div class="clearfix"></div>
                <hr>
                <div class="col-md-12 text-center">
                    <p style="color:#545B60">
                        <a href="http://devworms.com" target="_blank">DevWorms</a>
                    </p>
                </div>
        </div>

    </div>



    <!-- Login -->
    <div class="modal fade" id="ModalControl" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content" style="text-align: center; color:#000">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Acceso a Control General</h4>
                </div>

                <div class="modal-body">
                    <form method="post" id="login_form" action='../class/iniciar_sesion_master.php'>
                        <p><input type="email" class="form-control input-lg" id="correo" name="correo" placeholder="correo@devworms.com"></p>
                        <p><input type="password" class="form-control input-lg" id="contrasena" name="contrasena" placeholder="Password"></p>
                        <p>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                            <input type="submit" class="btn btn-primary" name="btn_acceder" id="btn_acceder" value="Acceder">
                            <hr />
                        </p>
                    </form>

                </div>
            </div>
        </div>
    </div>

    <!-- Login -->
    <div class="modal fade" id="ModalAdministrador" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content" style="text-align: center; color:#000">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Acceso al Administrador de Sucursales</h4>
                </div>

                <div class="modal-body">
                    <form method="post" id="login_form" action='../class/iniciar_sesion_admin.php'>
                        <p><input type="email" class="form-control input-lg" id="correo" name="correo" placeholder="correo@devworms.com"></p>
                        <p><input type="password" class="form-control input-lg" id="contrasena" name="contrasena" placeholder="Password"></p>
                        <p>
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
                            <input type="submit" class="btn btn-primary" name="btn_acceder" id="btn_acceder" value="Acceder">
                            <hr />
                        </p>
                    </form>

                </div>
            </div>
        </div>
    </div>

    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/postform.js"></script>
</body>
</html>