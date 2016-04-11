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
            <div class="col-md-2" id="leftCol">
                <br><br>
                  <ul class="nav nav-stacked" id="sidebar" style="background-color:#F8CE5E; font-size:20px">
                      <br><br>
                      <li><a href="#" id="imprimir"><i class="fa fa-print" style="color:#000"></i> Imprimir</a></li>      
                      <li><a href="#" id="recarga"><i class="fa fa-credit-card" style="color:#000"></i> Recarga</a></li>            
                      <li><a href="#" id="perfil"><i class="fa fa-user" style="color:#000"></i> Perfil</a></li>
                      <li><a href="#" id="historial"><i class="fa fa-history" style="color:#000"></i> Historial</a></li>
                      <hr>                  
                      <li><a href="#" id="acerca"><i class="fa fa-info-circle" style="color:#000"></i> Acerca de</a></li>
                      <li><a href="../class/cerrar_sesion.php"  style="color:#FF0000"><i class="fa fa-sign-out" ></i> Salir</a></li> 
                  </ul>
            </div>

            <br><br>
            <div class="col-md-5" id="page_imprimir">
                
                <div class="panel panel-default">
                    <div class="panel-body">

                        <h2>Impresión</h2>
                        <hr>
                        <p id="saldo">   <h4> Saldo: $<?php echo ObtenerSaldos($_SESSION["Id"], "saldo");  ?> </h4> </p>
                        <p id="regalo">  <h4> Saldo de regalo: $<?php echo ObtenerSaldos($_SESSION["Id"], "regalo"); ?>  </h4>  </p>
                        <hr>

                        <form action="../class/SubirImpresion.php" method="POST" enctype="multipart/form-data" onsubmit="return validacion( <?php echo ObtenerSaldos($_SESSION["Id"], "funcion");  ?> )">

                            <div class="step well" style="width: 100%; height: 300%; overflow: scroll;">
                                <div class="row">
                                    <table class= "table table-striped" style="table-layout: fixed;">
                                      <thead>
                                        <tr>
                                          <th  width="50%">Sucursal</th>
                                          <th  align="center"> Abierto</th>
                                          <th  align="center"> B / N</th>
                                          <th  align="center"> Color</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <?php   echo ImpresionSucursal();    ?>
                                    </tbody>
                                      </table>
                                </div>                    
                            </div>

                            <div class="step well">

                                <div class="row">

                                    <div class="form-group">
                                        <label for="documento">Seleccionar documento PDF o Word</label>
                                        <input type="file" id="documento" name="documento" accept=".doc, .docx, .pdf" required>
                                    </div><br>

                                    <div class="input-group">
                                        <span class="input-group-addon">No. de Hojas</span>
                                        <input type="number" id="hojas" name="hojas" class="form-control" value="1" aria-describedby="basic-addon1" step="1" min="1" max="99" required >
                                    </div><br>

                                    <div class="input-group">
                                        <span class="input-group-addon">Intervalo de Páginas</span>
                                        <input type="text" id="intervalo" name="intervalo" class="form-control" placeholder="1-5, 3-6-9 (Opcional)" aria-describedby="basic-addon1">
                                    </div><br>

                                    <div class="[ form-group ]">
                                        <input type="checkbox" name="blanconegro" id="blanconegro" autocomplete="off" checked />
                                        <div class="[ btn-group ]">
                                            <label for="blanconegro" class="[ btn btn-default active ]">
                                                Blanco y Negro
                                            </label>
                                            <label for="blanconegro" class="[ btn btn-primary ]">
                                                <span class="[ glyphicon glyphicon-plus ]"></span>
                                                <span class="[ glyphicon glyphicon-minus ]"></span>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="[ form-group ]">
                                        <input type="checkbox" name="color" id="color" autocomplete="off" />
                                        <div class="[ btn-group ]">
                                            <label for="color" class="[ btn btn-default active ]">
                                                Color
                                            </label>
                                            <label for="color" class="[ btn btn-primary ]">
                                                <span class="[ glyphicon glyphicon-plus ]"></span>
                                                <span class="[ glyphicon glyphicon-minus ]"></span>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="[ form-group ]">
                                        <input type="checkbox" name="caratula" id="caratula" autocomplete="off" />
                                        <div class="[ btn-group ]">
                                            <label for="caratula" class="[ btn btn-default active ]">
                                                Carátula a color
                                            </label>
                                            <label for="caratula" class="[ btn btn-primary ]">
                                                <span class="[ glyphicon glyphicon-plus ]"></span>
                                                <span class="[ glyphicon glyphicon-minus ]"></span>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="[ form-group ]">
                                        <input type="checkbox" name="lados" id="lados" autocomplete="off" />
                                        <div class="[ btn-group ]">
                                            <label for="lados" class="[ btn btn-default active ]">
                                                Ambos Lados
                                            </label>
                                            <label for="lados" class="[ btn btn-primary ]">
                                                <span class="[ glyphicon glyphicon-plus ]"></span>
                                                <span class="[ glyphicon glyphicon-minus ]"></span>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="[ form-group ]">
                                        <input type="checkbox" name="carta" id="carta" autocomplete="off" checked />
                                        <div class="[ btn-group ]">
                                            <label for="carta" class="[ btn btn-default active ]">
                                                Tamaño Carta
                                            </label>
                                            <label for="carta" class="[ btn btn-primary ]">
                                                <span class="[ glyphicon glyphicon-plus ]"></span>
                                                <span class="[ glyphicon glyphicon-minus ]"></span>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="[ form-group ]">
                                        <input type="checkbox" name="oficio" id="oficio" autocomplete="off" />
                                        <div class="[ btn-group ]">
                                            <label for="oficio" class="[ btn btn-default active ]">
                                                Tamaño Oficio
                                            </label>
                                            <label for="oficio" class="[ btn btn-primary ]">
                                                <span class="[ glyphicon glyphicon-plus ]"></span>
                                                <span class="[ glyphicon glyphicon-minus ]"></span>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="input-group" id="divCostoTotal">
                                    </div>

                                    <div>
                                        <input type="hidden" id="usuario" name="usuario" value="<?php echo $_SESSION['Id']; ?>"/>
                                        <input type="hidden" id="llave" name="llave" value="<?php echo $_SESSION['Llave']; ?>"/>
                                    </div>

                                </div>
                                 
                                <div style="text-align:right">   
                                    <input type="submit" class="btn btn-success" id="subir" name="subir" value="Imprimir"/>
                                </div>
                                       
                            </div>
                        </form> 
                        
                        <div style="text-align:right">
                            <button class="action back btn btn-info" id="btnBack" style="display:none">Atrás</button>   
                            <button class="action next btn btn-info" id="btnSiguiente" style="display:none">Siguiente</button>
                        </div>
                    </div> 
                </div>

            </div>
            

            <div class="col-md-5" id="page_recarga">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Recarga</h2>
                        <hr>

                        <div class="well">

                            <p>
                                <h3 style="text-align: center">Por el momento, únicamente aceptamos prepago desde una de nuestras sucursales.</h3>
                            </p>
                            <hr>
                            <p>
                                <h4 style="text-align: center">Acude a una de nuestras sucursales y realiza recargas de $5, $10, $20, $50 y $100.</h4>
                            </p>
                            <br>
                            <ol>
                                <?php echo ListarSucursales();  ?>
                            </ol>

                         </div>

                        <!--
                        <div class="well">
                            <p>
                                <img src="../img/pp_logo.png" width="176" height="50">
                            </p>

                            <div id="paypal">
                                <form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
                                    <input type="hidden" name="cmd" value="_s-xclick">
                                    <input type="hidden" name="hosted_button_id" value="4U4DUHLZRMLSW">
                                    <table>
                                        <tr><td><input type="hidden" name="on0" value="Prepago"></td></tr><tr><td><select name="os0">
                                            <option value="Prepago">Prepago $30.00 MXN</option>
                                            <option value="Prepago">Prepago $50.00 MXN</option>
                                            <option value="Prepago">Prepago $100.00 MXN</option>
                                        </select> </td></tr>
                                    </table>
                                    <br>
                                    <input type="hidden" name="currency_code" value="MXN">
                                    <input type="image" src="https://www.paypalobjects.com/es_XC/MX/i/btn/btn_buynowCC_LG.gif" border="0" name="submit" alt="PayPal, la forma más segura y rápida de pagar en línea.">
                                    <img alt="" border="0" src="https://www.paypalobjects.com/es_XC/i/scr/pixel.gif" width="1" height="1">
                                </form>                                
                            </div>
                            <br><br>

                            <p>
                                <img src="../img/op_logo.png" width="190" height="45">
                            </p>

                         </div>
                         -->
                    </div>
                </div>
            </div>


            <div class="col-md-5" id="page_perfil">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Perfil</h2>
                        <hr>

                        <div class="well" style="text-align:center">

                            <p>
                                <h1><?php   echo $_SESSION["Nombre"];   ?></h1>
                            </p>

                            <p>
                                <?php   echo $_SESSION["Correo"];   ?>
                            </p>

                            <p> 
                                Teléfono Asignado: <?php echo $_SESSION["Telefono"];  ?>
                            </p>

                            <p> 
                                Saldo: $<?php echo ObtenerSaldos($_SESSION["Id"], "saldo");  ?>
                            </p>

                            <p>
                                Saldo de regalo: $<?php echo ObtenerSaldos($_SESSION["Id"], "regalo");  ?>
                            </p>

                         </div>
                    </div>
                </div>
            </div>

            <div class="col-md-9" id="page_historial">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <h2>Historial</h2>
                        <hr>

                        <div class="well">

                            <table class="table table-hover">
                                <thead >
                                    <tr>
                                        <th>Folio</th>
                                        <th>Archivo</th>
                                        <th>Costo</th>
                                        <th>Fecha</th>
                                        <th>Lugar</th>
                                        <th>Estado</th>
                                    </tr>
                                </thead>

                                <tbody>                                    
                                    <?php echo ObtenerHistorialUsuario($_SESSION["Id"]);  ?>
                                </tbody>
                            </table>
                         </div>
                    </div>
                </div>
            </div>

            <div class="col-md-7" id="page_acerca">
                <div class="panel panel-default">
                    <div class="panel-body">

                        <div class="well" style="text-align:center">
                            <p>
                                <img src="../img/HurryPrintLogo.png" height="100" width="300" > <h3>HurryPrint</h3> Servicio de impresiones por prepago perteneciente a <strong>HurryApp</strong>.
                            </p>
                            <hr>
                            <p>
                                <img src="../img/HurryApp.png" height="131" width="400"> <h3>HurryApp</h3> Suite de servicios especializados en prepago, lealtad, ubicación y CRM móvil; propiedad de <strong>DevWorms S.A. de C.V.</strong>
                            </p>
                            <hr>
                            <p>
                                <img src="../img/LogoDW.png"> <h3>DevWorms S.A. de C.V.</h3> Empresa de base tecnológica.
                            </p>

                         </div>

                    </div>
                </div>
            </div>

        </div>
    </div>

    <script src="../js/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/postform.js"></script>
    <script src="../js/multistep.js"></script>
    <script src="../js/scripts.js"></script>


    
    <!--
    
        JSCRIPTING MENU
    
    -->

    <script>

        $("#page_imprimir").show();
        $("#page_recarga").hide();
        $("#page_perfil").hide();            
        $("#page_historial").hide();            
        $("#page_acerca").hide();

        $("#imprimir").click(function(){
            $("#page_imprimir").show();
            $("#page_recarga").hide();
            $("#page_perfil").hide();            
            $("#page_historial").hide();        
            $("#page_acerca").hide();
        });

        $("#recarga").click(function(){
            $("#page_recarga").show();
            $("#page_imprimir").hide();
            $("#page_perfil").hide();            
            $("#page_historial").hide();        
            $("#page_acerca").hide();
        });

        $("#perfil").click(function(){
            $("#page_perfil").show();
            $("#page_imprimir").hide();
            $("#page_recarga").hide();            
            $("#page_historial").hide();        
            $("#page_acerca").hide();
        });

        $("#historial").click(function(){
            $("#page_historial").show();
            $("#page_imprimir").hide();
            $("#page_perfil").hide();            
            $("#page_recarga").hide();        
            $("#page_acerca").hide();
        });

        $("#acerca").click(function(){
            $("#page_acerca").show();
            $("#page_imprimir").hide();
            $("#page_perfil").hide();            
            $("#page_recarga").hide();        
            $("#page_historial").hide();
        });

        $('#documento').change(function () {
            var val = $(this).val().toLowerCase();
            var regex = new RegExp("(.*?)\.(docx|doc|pdf)$");
                if(!(regex.test(val))) {
                    $(this).val('');
                    alert('Solamente acepta archivos PDF y Word');
                }
        });

        function validacion(saldoFinal){

            //      VALIDACIONES DE SALDO
            var precio;

            if($("#blanconegro").is(":checked") ) {   
                precio = ($("#hojas").val() * 1.10);
            }

            if($("#color").is(":checked") ) {   
                precio = ($("#hojas").val() * 5.10);
            }

            if($("#caratula").is(":checked") ) {   
                precio = ($("#hojas").val() * 1.0);
                precio += 5.0;
            }

            if(saldoFinal < precio ) {   
                alert ("No tienes saldo suficiente; haz una recarga de saldo");
                    return false;
            }

            //  VALIDACIONES DE TIPO
            if( !($("#blanconegro").is(":checked")) ) {                
                if( !($("#color").is(":checked")) ) {
                    alert('Selecciona el color de tu impresión');
                    return false;
                }
            }

            if( !($("#carta").is(":checked")) ) {
                if( !($("#oficio").is(":checked")) ) {
                    alert('Selecciona el tamaño de tu impresión');
                    return false;
                }
            }

            actualizarTotal(precio);

            if (confirm("Costo total: $" + precio + 0 + " \n" + "¿Confirmar Impresión?" ) == true) {
                return true;
            } else {
                return false;
            }            
        }


        function actualizarTotal(precio) {
            document.getElementById("divCostoTotal").innerHTML = 

                '<input type="hidden" id="totalimpresion" name="totalimpresion" class="form-control" value = "'+ precio +'">';
                //<input type='text' id='precio' name='precio' class='form-control' value = '0' aria-describedby='basic-addon1' disabled>";
        }


    </script>

</body>
</html>