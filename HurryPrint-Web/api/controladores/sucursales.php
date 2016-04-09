<?php

class sucursales     {

    const CODIGO_EXITO = 1;
    const ESTADO_EXITO = 1;
    const ESTADO_ERROR = 2;
    const ESTADO_ERROR_BD = 3;
    const ESTADO_ERROR_PARAMETROS = 4;
    const ESTADO_NO_ENCONTRADO = 5;


    public static function get($peticion)   {

        $idUsuario = usuarios::autorizar();
        return self::obtenerSucursales($idUsuario);

    }


    private function obtenerSucursales($idUsuario)    {
                $comando = "SELECT a.id_tienda, a.nombre_tienda, a.direccion, a.lat, a.lng, b.abierto, b.color, b.blanco_negro FROM tienda a inner join estatus_tienda b on a.id_tienda = b.id_tienda";


                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                $sentencia->execute();
                $resultado = $sentencia->fetchAll(PDO::FETCH_ASSOC);
                
                return 
                [
                    "estado" => 1,
                    "sucursal" => $resultado
                ];  

    }

}

?>
