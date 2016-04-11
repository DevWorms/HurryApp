<?php

require 'datos/ConexionBD.php';

    class usuarios  {

        //  ++++++++++++++++++      INICIO DECLARACIÓN DE VARIABLES CONSTANTES     ++++++++++++++++++++++

            const NOMBRE_TABLA = "usuario";
            const WALLET = "wallet";
            const ID_USUARIO = "id_Usuario";
            const CONTRASENA = "contrasena";
            const NOMBRE = "nombre";
            const CLAVE_API = "claveApi";
            const TELEFONO = "telefono";    
            const TOKEN = "token_acceso";

            const ESTADO_CREACION_EXITOSA = 1;
            const ESTADO_CREACION_FALLIDA = 2;
            const ESTADO_ERROR_BD = 3;
            const ESTADO_AUSENCIA_CLAVE_API = 4;
            const ESTADO_CLAVE_NO_AUTORIZADA = 5;
            const ESTADO_URL_INCORRECTA = 6;
            const ESTADO_FALLA_DESCONOCIDA = 7;
            const ESTADO_PARAMETROS_INCORRECTOS = 8;

        //  ++++++++++++++++++      FIN DECLARACIÓN DE VARIABLES CONSTANTES     ++++++++++++++++++++++
           

            //  SELECCIONAR PETICIÓN POST
             public static function post($peticion)        {
                if ($peticion[0] == 'registro') {
                    return self::registrar();
                } else if ($peticion[0] == 'login') {
                    return self::loguear();
                } else {
                    throw new ExcepcionApi(self::ESTADO_URL_INCORRECTA, "Url mal formada", 400);
                }
            }


        //  ++++++++++++++++++      INICIO FUNCIONES REGISTRO DE USUARIO     ++++++++++++++++++++++

            //  MÓDULO API PARA REGISTRAR USUARIO
            private function registrar()    {
                $cuerpo = file_get_contents('php://input'); //  Obtener fichero de la petición a cadena
                $usuario = json_decode($cuerpo); // Decodificar a JSON el fichero
                $resultado = self::crear($usuario); // Llamar función "crear()" con parámetro JSON

                if ($resultado) {

                    if($resultado["Exito"] = self::ESTADO_CREACION_EXITOSA )    {
                        http_response_code(200);
                        $telefono = $resultado["Telefono"];                        
                        $apikey = $resultado["Apikey"];
                        return
                            [
                                "estado" => self::ESTADO_CREACION_EXITOSA,
                                "mensaje" => utf8_encode("¡Registro con éxito!"),
                                "APIkey" => $apikey,
                                "Telefono" => $telefono
                            ];
                     }

                    else if($resultado["Exito"] = self::ESTADO_CREACION_FALLIDA) {
                        throw new ExcepcionApi(self::ESTADO_CREACION_FALLIDA, "Ha ocurrido un error");
                    }

                    else
                        throw new ExcepcionApi(self::ESTADO_FALLA_DESCONOCIDA, "Falla desconocida", 400);
                }
            }


            // FUNCION "crear()" PARA REGISTRAR NUEVO USUARIO EN LA BD
            private function crear($datosUsuario)    {

                //  Obtener datos del JSON y encriptar en B64
                $contrasena = base64_encode($datosUsuario->contrasena);  
                $nombre = base64_encode($datosUsuario->nombre);   
                //  Obtener contrasena
                $telefono = $datosUsuario->telefono; 
                //  Obtener token del JSON y encriptar con HASH de una sola vía
                $token = self::encriptador($datosUsuario->token);  
                //  Generar clave API
                $claveApi = self::generarClaveApi();

                try {

                    $pdo = ConexionBD::obtenerInstancia()->obtenerBD();

                    // Sentencia INSERT
                    $comando = "INSERT INTO " . self::NOMBRE_TABLA . " ( " .
                        self::CONTRASENA . "," .
                        self::NOMBRE . "," .
                        self::CLAVE_API . "," .
                        self::TELEFONO . "," .
                        self::TOKEN . ")" .
                        " VALUES(?,?,?,?,?)";


                    $sentencia = $pdo->prepare($comando);

                        $sentencia->bindParam(1, $contrasena);
                        $sentencia->bindParam(2, $nombre);
                        $sentencia->bindParam(3, $claveApi);
                        $sentencia->bindParam(4, $telefono);
                        $sentencia->bindParam(5, $token);

                    $resultado = $sentencia->execute();

                    $id = self::obtenerIdUsuario($claveApi);

                    if ($resultado) {
                         self::agregarWallet($id);
                         // DevolverArray
                         return $arrayRetorno = [
                                                        "Exito" => self::ESTADO_CREACION_EXITOSA,
                                                        "Apikey" => $claveApi,
                                                        "Telefono" => $telefono
                                                    ];
                    } else {
                        return self::ESTADO_CREACION_FALLIDA;
                    }
                } catch (PDOException $e) {
                    throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());
                }
            }


            // ENCRIPTADOR "password_hash()"
            private function encriptador($palabra)    {
                if ($palabra)
                    return password_hash($palabra, PASSWORD_DEFAULT);
                else return null;
            }


            // GENERAR API KEY
            private function generarClaveApi()    {
                return md5(microtime() . rand());
            }

        //  ++++++++++++++++++      FIN FUNCIONES REGISTRO DE USUARIO     ++++++++++++++++++++++


        //  ++++++++++++++++++      INICIO FUNCIONES LOGIN DE USUARIO     ++++++++++++++++++++++

            // FUNCION DE LOGIN
            private function loguear()    {
                $respuesta = array();

                $body = file_get_contents('php://input');
                $usuario = json_decode($body);

                $telefono = $usuario->telefono;
                $contrasena = $usuario->contrasena;

                if (self::autenticar($telefono, $contrasena)) {
                    $usuarioBD = self::obtenerUsuarioPorTelefono($telefono);

                    if ($usuarioBD != NULL) {
                        http_response_code(200);
                        $respuesta["Usuario"] = $usuarioBD["id_Usuario"];
                        $respuesta["Nombre"] = base64_decode($usuarioBD["nombre"]);
                        $respuesta["Telefono"] = $usuarioBD["telefono"];
                        $respuesta["APIkey"] = $usuarioBD["claveApi"];
                        return ["estado" => 1, "usuario" => $respuesta];

                    } else {
                        throw new ExcepcionApi(self::ESTADO_FALLA_DESCONOCIDA,
                            "Ha ocurrido un error");
                    }
                } else {
                    throw new ExcepcionApi(self::ESTADO_PARAMETROS_INCORRECTOS,
                        utf8_encode("Teléfono o contraseña inválidos"));
                }
            }

            //  AUTENTICAR TELEFONO Y CONTRASEÑA
            private function autenticar($telefono, $contrasena)    {
                $comando = "SELECT contrasena FROM " . self::NOMBRE_TABLA .
                    " WHERE " . self::TELEFONO . "=?";

                try {

                    $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                    $sentencia->bindParam(1, $telefono);
                    $sentencia->execute();

                        if ($sentencia) {
                            $resultado = $sentencia->fetch();

                            if($contrasena == base64_decode($resultado['contrasena']))   {
                                return true;
                            }   else return false;

                        } else return false;

                } catch (PDOException $e) {
                    throw new ExcepcionApi(self::ESTADO_ERROR_BD, $e->getMessage());

                }
            }

            private function validarToken($tokenPlano, $tokenHash)    {
                return password_verify($tokenPlano, $tokenHash);
            }


            private function obtenerUsuarioPorTelefono($telefono)    {
                $comando = "SELECT " .
                    self::ID_USUARIO . "," .
                    self::NOMBRE . "," .
                    self::TELEFONO . "," .
                    self::CLAVE_API .
                    " FROM " . self::NOMBRE_TABLA .
                    " WHERE " . self::TELEFONO . "=?";

                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                $sentencia->bindParam(1, $telefono);

                if ($sentencia->execute())
                    return $sentencia->fetch(PDO::FETCH_ASSOC);
                else
                    return null;
            }


            //OTORGAR PERMISOS PARA EL ACCESO A RECURSOS
            public static function autorizar()    {
                $cabeceras = apache_request_headers();

                if (isset($cabeceras["Apikey"])) {

                    $claveApi = $cabeceras["Apikey"];

                    if (usuarios::validarClaveApi($claveApi)) {
                        return usuarios::obtenerIdUsuario($claveApi);
                    } else {
                        throw new ExcepcionApi(
                            self::ESTADO_CLAVE_NO_AUTORIZADA, "Clave de API no autorizada", 401);
                    }

                } else {
                    throw new ExcepcionApi(
                        self::ESTADO_AUSENCIA_CLAVE_API,
                        utf8_encode("Se requiere Clave del API para autenticación"));
                }
            }


            // COMPRUEBA EXISTENCIA API
            private function validarClaveApi($claveApi)    {
                $comando = "SELECT COUNT(" . self::ID_USUARIO . ")" .
                    " FROM " . self::NOMBRE_TABLA .
                    " WHERE " . self::CLAVE_API . "=?";

                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                $sentencia->bindParam(1, $claveApi);
                $sentencia->execute();

                return $sentencia->fetchColumn(0) > 0;
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

            // CREAR NUEVO WALLET
            private function agregarWallet($idUsuario)    {
                $comando = "INSERT INTO " . self::WALLET . " ( " .
                        self::ID_USUARIO . ")" .
                        " VALUES(?)";

                    $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                    $sentencia->bindParam(1, $idUsuario);
                    $sentencia->execute();          
            }

        //  ++++++++++++++++++      FIN FUNCIONES LOGIN DE USUARIO     ++++++++++++++++++++++

    }
?>