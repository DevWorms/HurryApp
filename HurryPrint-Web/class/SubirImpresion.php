<?php
    include('Connector.php');
    $db = new Connector();
    date_default_timezone_set('America/Mexico_City');

    //comprobamos si ha ocurrido un error.
    if ($_FILES["documento"]["error"] > 0)  {
    	echo "ha ocurrido un error";

    }    else {

            //  OBTENER INFORMACIÓN DEL USUARIO Y VALIDAR
            $usuario = $_POST['usuario'];
            $llave = $_POST['llave'];

            //  VALIDAMOS INFORMACIÓN REAL DEL USUARIO
            if(comprobarUsuario($usuario, $llave))  {

                    //  OBTENER DÍA DE IMPRESIÓN Y CARPETA DONDE SE ALMACENARÁ                    
                    if(date("w") == 0)
                        $carpeta = "d/";
                    if(date("w") == 1)
                        $carpeta = "l/";
                    if(date("w") == 2)
                        $carpeta = "m/";
                    if(date("w") == 3)
                        $carpeta = "mm/";
                    if(date("w") == 4)
                        $carpeta = "j/";
                    if(date("w") == 5)
                        $carpeta = "v/";
                    if(date("w") == 6)
                        $carpeta = "s/";

                    //  OBTENER INFORMACIÓN DEL FORMULARIO
                    $sucursal = $_POST['sucursal'];
                    $hojas = $_POST['hojas'];
                    $intervalo = $_POST['intervalo'];
                    $total = $_POST['totalimpresion'];

                    if (isset($_POST['blanconegro']))   {
                            $blanconegro = "1";
                    } else {
                            $blanconegro = "0";
                    }

                    if (isset($_POST['color'])) {
                            $color = "1";
                    } else {
                            $color = "0";
                    }

                    if (isset($_POST['caratula']))  {
                            $caratula = "1";
                    } else {
                            $caratula = "0";
                    }

                    if (isset($_POST['lados'])) {
                            $lados = "1";
                    } else {
                            $lados = "0";
                    }

                    if (isset($_POST['carta'])) {
                            $carta = "1";
                    } else {
                            $carta = "0";
                    }

                    if (isset($_POST['oficio']))    {
                            $oficio = "1";
                    } else {
                            $oficio = "0";
                    }

                    // VARIABLES PARA ALMACENAR LA INFORMACIÓN EN LA BD Y EN EL FICHERO
                    $limite_kb = 4096000;
                    $folio = rand(0,9999) . $usuario;
                    $ruta = "../documentos/";
                    $nombre_doc = $_FILES['documento']['name'];
                    $extension = "." . end(explode('.',$_FILES['documento']['name']));

                    //  VALIDAR SI EL TAMAÑO DEL AARCHIVO ES MAYOR AL LIMITE
                    if ($_FILES['documento']['size'] <= $limite_kb) {

                        $webOffice = "http://view.officeapps.live.com/op/view.aspx?src=http://hurryprint.devworms.com/documentos/" . $carpeta . $folio . $extension;
                        $url = $ruta . $carpeta . $folio . $extension;

                        //  MOVER ARCHIVO A URL EN CARPETA
                        $resultado = @move_uploaded_file($_FILES["documento"]["tmp_name"], $url);

                        //  CORROBORAR SI EL ARCHIVO SE OBTUVO SATISFACTORIAMENTE
                        if ($resultado)  {

                            //  DECLARAR VARIABLE PARA MIRAR EL DOCUMENTO
                            if($extension == ".pdf")
                                $ver = $url;
                            else
                                $ver = $webOffice;


                            //  QUERY PARA ALMACENAR LA OPERACION
                            $operacion = "INSERT INTO operacion(id_tienda, id_tipo_operacion,id_usuario) VALUES (".$sucursal.",1,".$usuario.")";
                            $db->execute($operacion);

                            //  QUERY PARA OBTENER EL ID DE LA OPERACION
                            $idoperacion = "";
                            $consultaop = "SELECT * FROM operacion WHERE id_tienda = " .$sucursal. " AND id_tipo_operacion = 1 AND id_usuario = ".$usuario." ORDER BY  fecha_operacion DESC LIMIT 0 , 1;";
                            $db->execute($consultaop);

                                // VERIFICA SI EL QUERY ES VÁLIDO
                                if ($result->num_rows >= 1) {

                                    //  LANZA LA OPERACIÓN DE IMPRESIÓN
                                    while ($resultado = $result->fetch_assoc())     {
                                            $idoperacion = $resultado['id_operacion'];
                                    }
                                }   else
                                        echo "La operación no fue almacenada";

                            //  QUERY PARA ALMACENAR INFORMACIÓN DEL DOCUMENTO
                            $nombre = $db->sec($_FILES['documento']['name']);
                            $consulta = "INSERT INTO documentos(folio_documento, nombre_documento, url_documento,  ver, numero_hojas, rango_hojas, blanco_negro, color, caratula_color, ambos_lados, carta, oficio, id_operacion) 
                                             VALUES ('".$folio."' , '".$nombre."' , '".$url."' , '".$ver."' , '".$hojas."' , '".$intervalo."' , '".$blanconegro."' , '".$color."' , '".$caratula."' , '".$lados."' , '".$carta."' , '".$oficio."' , '".$idoperacion."')";                            
                            $db->execute($consulta);

                            //  QUERY PARA ACTUALIZAR SALDOS
                           $storeSaldo = "CALL hurryapp.actualizar_saldos(".$total.", ".$usuario.")";
                           $db->execute($storeSaldo);

                        }   else
                                echo "El archivo no fue copiado; consulta si hay errores en el mismo. Solamente aceptamos Word y PDF";

                    }   else
                            echo "Excede el tamaño limite de 4mb";
            }   else
                    echo "El usuario y llave no coinciden";
        }

        function comprobarUsuario($usuario, $llave) {
            global $db;
            global $result;

            $consulta = "SELECT * FROM usuario WHERE id_usuario = '" . $usuario . "' AND claveApi = '" . $llave . "';";
            $db->execute($consulta);                

            if ($result->num_rows == 1)
                return true;
            else
                return false;
        }

        echo "<script>
	           window.location = '../user'
	       </script>";

    ?>