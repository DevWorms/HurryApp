<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>HurryApp 0.1</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/styles.css" rel="stylesheet">   
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
</head>
<body>

    <nav class="navbar navbar-default navbar-fixed-top" role="banner" style="background-color:#F8CE5E;">
        <div class="container">

            <a href="/" class="navbar-brand">HurryApp 0.1</a>

            <nav class="collapse navbar-collapse" role="navigation">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="#sec">Get Started</a>
                    </li>
                    <li>
                        <a href="#sec">Edit</a>
                    </li>
                </ul>
            </nav>

        </div>
    </nav>


    <div class="container">
        <div class="row">

            <!-- MENÚ IZQUIERDO -->
            <div class="col-md-3" id="leftCol">
                <br><br>
                  <ul class="nav nav-stacked" id="sidebar" style="background-color:#F8CE5E;">
                      <br><br>
                      <li><a href="#" id="recarga">Recarga</a></li>
                      <li><a href="#" id="imprimir">Imprimir</a></li>                  
                      <li><a href="#" id="perfil">Perfil</a></li>
                      <li><a href="#" id="historial">Historial</a></li>
                      <hr>                  
                      <li><a href="#" id="acerca">Acerca de</a></li>
                      <li><a href="#" id="creditos">Créditos</a></li>
                  </ul>
            </div>

            <br><br>

            <div class="col-md-9" id="page_recarga">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>RECARGA</h2>
                        <hr>

                        <div class="well">
                        </div>
                    </div>
                </div>
            </div>


            <div class="col-md-5" id="page_imprimir">
                
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>IMPRESIÓN</h2>
                        <hr>

                        <div class="well">              
                        </div>
                    </div> 
                </div>

            </div>


            <div class="col-md-9" id="page_perfil">

                <h2 id="sec0">Perfil</h2>
                <p>
                    Tú perfil
                </p>

            </div>

            <div class="col-md-9" id="page_historial">

                <h2 id="sec0">Historial</h2>
                <p>
                    El historial
                </p>

            </div>

        </div>
    </div>

    <script src="../js/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/scripts.js"></script>
    <script src="../js/postform.js"></script>
    <script src="../js/multistep.js"></script>

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

    <!--
        Colores:
        #DD3C2A
        #A42222
        #7F0E10
    -->


    <!--


        <div class="[ form-group ]">
            <input type="checkbox" name="fancy-checkbox-success-custom-icons" id="fancy-checkbox-success-custom-icons" autocomplete="off" />
            <div class="[ btn-group ]">
                <label for="fancy-checkbox-success-custom-icons" class="[ btn btn-success ]">
                    <span class="[ glyphicon glyphicon-plus ]"></span>
                    <span class="[ glyphicon glyphicon-minus ]"></span>
                </label>
                <label for="fancy-checkbox-success-custom-icons" class="[ btn btn-default active ]">
                    Success Checkbox
                </label>
            </div>
        </div>

        <div class="[ form-group ]">
            <input type="checkbox" name="fancy-checkbox-info-custom-icons" id="fancy-checkbox-info-custom-icons" autocomplete="off" />
            <div class="[ btn-group ]">
                <label for="fancy-checkbox-info-custom-icons" class="[ btn btn-info ]">
                    <span class="[ glyphicon glyphicon-plus ]"></span>
                    <span class="[ glyphicon glyphicon-minus ]"></span>
                </label>
                <label for="fancy-checkbox-info-custom-icons" class="[ btn btn-default active ]">
                    Info Checkbox
                </label>
            </div>
        </div>

        <div class="[ form-group ]">
            <input type="checkbox" name="fancy-checkbox-warning-custom-icons" id="fancy-checkbox-warning-custom-icons" autocomplete="off" />
            <div class="[ btn-group ]">
                <label for="fancy-checkbox-warning-custom-icons" class="[ btn btn-warning ]">
                    <span class="[ glyphicon glyphicon-plus ]"></span>
                    <span class="[ glyphicon glyphicon-minus ]"></span>
                </label>
                <label for="fancy-checkbox-warning-custom-icons" class="[ btn btn-default active ]">
                    Warning Checkbox
                </label>
            </div>
        </div>

    -->