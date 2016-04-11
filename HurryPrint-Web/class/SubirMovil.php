<?php
    include('Connector.php');
    $db = new Connector();
    date_default_timezone_set('America/Mexico_City');

    //comprobamos si ha ocurrido un error.
    if ($_FILES["documento"]["error"] > 0)  {
        $estado =  
        [
            "Estado" => 2,
            "Descripcion" => "Hubo un error al cargar el documento"
        ];
        
        echo json_encode($estado);

    }    else {

                $llave = $_POST['llave'];

                //  OBTENER INFORMACIÓN DEL USUARIO Y VALIDAR
                $usuario = obtenerUsuario($llave);

                //  OBTENER CARPETA PARA ALMACENAR LA INFORMACIÓN
                $dia = date("w");
                $carpeta = obtenerCarpetaDia($dia);

                //  OBTENER INFORMACIÓN DEL FORMULARIO
                $sucursal = $_POST['sucursal'];
                $hojas = $_POST['hojas'];
                $intervalo = $_POST['intervalo'];
                $total = $_POST['totalimpresion'];
                $juegos = $_POST['juegos'];
                
                if ($juegos == "" || $juegos == "0" )
                        $juegos= "1";                

                if ($_POST['blanconegro'] == "1")   {
                        $blanconegro = "1";
                } else {
                        $blanconegro = "0";
                }

                if ($_POST['color'] == "1") {
                        $color = "1";
                } else {
                        $color = "0";
                }

                if ($_POST['caratula'] == "1")  {
                        $caratula = "1";
                } else {
                        $caratula = "0";
                }

                if ($_POST['lados']  == "1") {
                        $lados = "1";
                } else {
                        $lados = "0";
                }

                if ($_POST['carta'] == "1") {
                        $carta = "1";
                } else {
                        $carta = "0";
                }

                if ($_POST['oficio'] == "1")    {
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
                        $consulta = "INSERT INTO documentos(folio_documento, nombre_documento, url_documento,  ver, juegos, numero_hojas, rango_hojas, blanco_negro, color, caratula_color, ambos_lados, carta, oficio, id_operacion) 
                                         VALUES ('".$folio."' , '".$nombre."' , '".$url."' , '".$ver."' , '".$juegos."' , '".$hojas."' , '".$intervalo."' , '".$blanconegro."' , '".$color."' , '".$caratula."' , '".$lados."' , '".$carta."' , '".$oficio."' , '".$idoperacion."')";                            
                        $db->execute($consulta);

                        //  QUERY PARA ACTUALIZAR SALDOS
                       $storeSaldo = "CALL hurryapp.actualizar_saldos(".$total.", ".$usuario.")";
                       $db->execute($storeSaldo);
                       
                       	//  RESPUESTA DE ÉXITO
                        $exito =  
                        [
                            "Estado" => 1,
                            "Descripcion" => "Exito"
                        ];
                        
                        echo json_encode($exito);
                        

                    }   else    {
			        $estado =  
			        [
			            "Estado" => 3,
			            "Descripcion" => "Archivo no almacenado"
			        ];
			        
			        echo json_encode($estado);
                        }

                }   else    {
			        $estado =  
			        [
			            "Estado" => 4,
			            "Descripcion" => "Aechivo demasiado grande"
			        ];
			        
			        echo json_encode($estado);
                    }
            } 

        function obtenerUsuario($llave) {
            global $db;
            global $result;

            $consulta = "SELECT * FROM usuario WHERE claveApi = '" . $llave . "';";
            $db->execute($consulta);                

            if ($result->num_rows == 1) {

                while ($resultado = $result->fetch_assoc()) {
                            $usuario = $resultado['id_usuario'];
                    }
                    return $usuario;
            }   else    {
                return null;
            }
         }

         function obtenerCarpetaDia($palabra)    {

            if($palabra == 0)
                return $carpeta = "d/";
            else if($palabra == 1)
                return $carpeta = "l/";
            else if($palabra == 2)
                return $carpeta = "m/";
            else if($palabra == 3)
                return $carpeta = "mm/";
            else if($palabra == 4)
                return $carpeta = "j/";
            else if($palabra == 5)
                return $carpeta = "v/";
            else
                return $carpeta = "s/";

        }        

    ?>