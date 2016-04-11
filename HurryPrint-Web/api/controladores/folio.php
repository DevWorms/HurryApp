<?php

class folio     {

    const CODIGO_EXITO = 1;
    const ESTADO_EXITO = 1;
    const ESTADO_ERROR = 2;
    const ESTADO_ERROR_BD = 3;
    const ESTADO_ERROR_PARAMETROS = 4;
    const ESTADO_NO_ENCONTRADO = 5;


    public static function get($peticion)   {
        $cabecera = apache_request_headers();
        $folio = intval($cabecera["Folio"]);
        $idUsuario = usuarios::autorizar();

        return self::descripcionFolio($idUsuario, $folio);

    }


    private function descripcionFolio($idUsuario, $folio)    {
                $comando = "CALL descripcion_folios (?,?)";

                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                $sentencia->bindParam(1, $idUsuario);
                $sentencia->bindParam(2, $folio);
                $sentencia->execute();
                $resultado = $sentencia->fetch();
                
                $respuesta["Folio"] = $resultado["folio_documento"];
                $respuesta["Nombre"] = $resultado["nombre_documento"];
                $respuesta["Costo"] = $resultado["costo"];
                $respuesta["Fecha"] = $resultado["fecha"];
                $respuesta["Sucursal"] = $resultado["nombre_tienda"];
                $respuesta["Status"] = $resultado["descripcion"];
                
                return 
                [
                    "estado" => 1,
                    "descripcion" => $respuesta
                ];  
    }

}

?>
