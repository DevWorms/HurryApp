<?php

class folios     {

    const CODIGO_EXITO = 1;
    const ESTADO_EXITO = 1;
    const ESTADO_ERROR = 2;
    const ESTADO_ERROR_BD = 3;
    const ESTADO_ERROR_PARAMETROS = 4;
    const ESTADO_NO_ENCONTRADO = 5;


    public static function get($peticion)   {

        $idUsuario = usuarios::autorizar();
        return self::ListarFolios($idUsuario);

    }


    private function ListarFolios($idUsuario)    {
                $comando = "CALL listar_folios( ? )";

                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                $sentencia->bindParam(1, $idUsuario);
                $sentencia->execute();
                $resultado = $sentencia->fetchAll(PDO::FETCH_ASSOC);
                
                return 
                [
                    "estado" => 1,
                    "folios" => $resultado
                ];  

    }

}

?>
