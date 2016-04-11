<?php

require_once 'datos/ConexionBD.php';

    class impresion  {

        //  ++++++++++++++++++      INICIO DECLARACIÓN DE VARIABLES CONSTANTES     ++++++++++++++++++++++

            const NOMBRE_TABLA = "usuario";
            const ID_USUARIO = "id_Usuario";
            const CLAVE_API = "claveApi";

            const ESTADO_CREACION_EXITOSA = 1;
            const ESTADO_CREACION_FALLIDA = 2;
            const ESTADO_ERROR_BD = 3;
            const ESTADO_AUSENCIA_CLAVE_API = 4;
            const ESTADO_CLAVE_NO_AUTORIZADA = 5;
            const ESTADO_URL_INCORRECTA = 6;
            const ESTADO_FALLA_DESCONOCIDA = 7;
            const ESTADO_DOCUMENTO_LARGO = 8;
            const ESTADO_NO_DOCUMENTO = 9;

        //  ++++++++++++++++++      FIN DECLARACIÓN DE VARIABLES CONSTANTES     ++++++++++++++++++++++           

            //  SELECCIONAR PETICIÓN POST
             public static function post($peticion)        {
                if ($peticion[0] == 'subirImpresion') {
                    return self::subirImpresion();
                } else if ($peticion[0] == 'imprimir') {
                    return self::impresion();
                }else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
                }
            }

        //  ++++++++++++++++++      INICIO SUBIR DOCUMENTO DE IMPRESIÓN     ++++++++++++++++++++++

            private function subirImpresion()   {

                if ($_FILES["documento"]["error"] > 0)  {
                    throw new ExcepcionApi(self::ESTADO_NO_DOCUMENTO, "Hubo un error al cargar el documento", 400);
                }   else {

                    //LIMITAR A 4MB DE SUBIDA
                    $limite_kb = 4096000;

                    if ($_FILES['documento']['size'] <= $limite_kb) {

                        $apikey = $_POST['apikey'];
                        $usuario = self::obtenerIdUsuario($apikey);

                        //  OBTENER CARPETA PARA ALMACENAR LA INFORMACIÓN
                        $dia = date("w");
                        $carpeta = self::obtenerCarpetaDia($dia);

                        // VARIABLES PARA ALMACENAR LA INFORMACIÓN EN LA BD Y EN EL FICHERO
                        $folio = rand(0,9999) . $usuario;
                        $ruta = "../documentos/";
                        $nombre_doc = $_FILES['documento']['name'];
                        $extension = "." . end(explode('.',$_FILES['documento']['name']));
                        
                        $webOffice = "http://view.officeapps.live.com/op/view.aspx?src=http://hurryprint.devworms.com/documentos/" . $carpeta . $folio . $extension;
                        $url = $ruta . $carpeta . $folio . $extension;

                        $resultado = @move_uploaded_file($_FILES["documento"]["tmp_name"], $url);

                        if($resultado)  {

                            //  DECLARAR VARIABLE PARA MIRAR EL DOCUMENTO
                            if($extension == ".pdf")
                                $ver = $url;
                            else
                                $ver = $webOffice;

                            return 
                                    [
                                        "Estado"=> 1,
                                        "Folio" => $folio,
                                        "NombreDoc" => $nombre_doc,
                                        "URL" => $url,
                                        "Ver" => $ver
                                    ];
                        }   else {
                            throw new ExcepcionApi(self::ESTADO_CREACION_FALLIDA, "Hubo un error al cargar el documento", 400);
                        }


                    }   else
                            throw new ExcepcionApi(self::ESTADO_DOCUMENTO_LARGO, "Documento excede más de 4mb", 413);
                }

            }

            //OBTENER ID DE USUARIO POR MEDIO DE LA API KEY
            private function obtenerIdUsuario($claveApi)    {
                $comando = "SELECT " . self::ID_USUARIO .
                    " FROM " . self::NOMBRE_TABLA .
                    " WHERE " . self::CLAVE_API . "=?";

                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);

                $sentencia->bindParam(1, $claveApi);

                if ($sentencia->execute()) {
                    $resultado = $sentencia->fetch();
                    return $resultado['id_Usuario'];
                } else
                    return null;
            }

            // LANZAR CARPETA POR DÍA
        private function obtenerCarpetaDia($palabra)    {

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


        //  ++++++++++++++++++      INICIO FUNCIONES LOGIN DE IMPRESIÓN     ++++++++++++++++++++++

            //  MÓDULO API PARA REGISTRAR IMPRESION
        private function imprimir()    {
            $cuerpo = file_get_contents('php://input'); //  Obtener fichero de la petición a cadena
            $documento = json_decode($cuerpo); // Decodificar a JSON el fichero
            $resultado = self::crearImpresion($documento); // Llamar función "crear()" con parámetro JSON

            if ($resultado) {

                if($resultado["Exito"] = self::ESTADO_CREACION_EXITOSA )    {
                    http_response_code(200);
                    $folio = $resultado["folio"];
                    return
                        [
                            "estado" => self::ESTADO_CREACION_EXITOSA,
                            "mensaje" => utf8_encode("Impresión con éxito!"),
                            "Folio" => $folio
                        ];
                 }

                else if($resultado["Exito"] = self::ESTADO_CREACION_FALLIDA) {
                    throw new ExcepcionApi(self::ESTADO_CREACION_FALLIDA, "Ha ocurrido un error");
                }

                else
                    throw new ExcepcionApi(self::ESTADO_FALLA_DESCONOCIDA, "Falla desconocida", 400);
            }
        }


        // FUNCION "crear()" PARA REGISTRAR NUEVODOCUENTO EN LA BD
        private function crearImpresion($documento)    {

            //  Obtener datos del JSON

            $usuario = $documento->usuario;
            $folio = $documento->folio;
            $nombre = $documento->nombre_doc;
            $url = $documento->url;
            $ver = $documento->ver;
            $sucursal = $documento->sucursal;
            $hojas = $documento->hojas;   
            $intervalo = $documento->intervalo; 
            $blanconegro = $documento->blanconegro;
            $color = $documento->color;
            $caratula = $documento->caratula;
            $lados = $documento->lados;
            $carta = $documento->carta;
            $oficio = $documento->oficio;
            $total = $documento->total;

            try {

                $pdo = ConexionBD::obtenerInstancia()->obtenerBD();

                // ALMACENAR LA OPERACIÓN
                $comando1 = "INSERT INTO operacion(id_tienda, id_tipo_operacion,id_usuario) VALUES(?,?,?)";
                $idOp = 1;
                $sentencia = $pdo->prepare($comando1);
                    $sentencia->bindParam(1, $sucursal);
                    $sentencia->bindParam(2, $idOp);
                    $sentencia->bindParam(3, $usuario);
                $resultado = $sentencia->execute();

                // OBTENER ID DE OPERACIÓN
                $id_operacion = $pdo->lastInsertId();

                //  QUERY PARA ALMACENAR INFORMACIÓN DEL DOCUMENTO
                $comando2 = "INSERT INTO documentos(folio_documento, nombre_documento, url_documento,  ver, numero_hojas, rango_hojas, blanco_negro, color, caratula_color, ambos_lados, carta, oficio, id_operacion) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                
                $sentencia2 = $pdo->prepare($comando2);
                    $sentencia2->bindParam(1, $folio);
                    $sentencia2->bindParam(2, $nombre);
                    $sentencia2->bindParam(3, $url);
                    $sentencia2->bindParam(4, $ver);
                    $sentencia2->bindParam(5, $hojas);
                    $sentencia2->bindParam(6, $intervalo);
                    $sentencia2->bindParam(7, $blanconegro);
                    $sentencia2->bindParam(8, $color);
                    $sentencia2->bindParam(9, $caratula);
                    $sentencia2->bindParam(10, $lados);
                    $sentencia2->bindParam(11, $carta);
                    $sentencia2->bindParam(12, $oficio);
                    $sentencia2->bindParam(13, $id_operacion);
                $resultado2 = $sentencia2->execute();

                
                //  QUERY PARA ACTUALIZAR SALDOS
                $comando3 = "CALL hurryapp.actualizar_saldos(?,?)";

                $sentencia3 = $pdo->prepare($comando3);
                    $sentencia3->bindParam(1, $total);
                    $sentencia3->bindParam(2, $usuario);
                $resultado3 = $sentencia3->execute();

                return $arrayRetorno = [
                                                "Exito" => self::ESTADO_CREACION_EXITOSA,
                                                "Folio" => $claveApi
                                            ];
           
            } catch (PDOException $e) {
                throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
            }
        }

        //  ++++++++++++++++++      FIN FUNCIONES LOGIN DE USUARIO     ++++++++++++++++++++++

    }
?>