<?php

class saldo     {

    const CODIGO_EXITO = 1;
    const ESTADO_EXITO = 1;
    const ESTADO_ERROR = 2;
    const ESTADO_ERROR_BD = 3;
    const ESTADO_ERROR_PARAMETROS = 4;
    const ESTADO_NO_ENCONTRADO = 5;


    public static function get($peticion)   {

        $idUsuario = usuarios::autorizar();
        return self::obtenerSaldo($idUsuario);

    }


    private function obtenerSaldo($idUsuario)    {
                $comando = "SELECT * FROM wallet WHERE id_usuario = ?";

                $sentencia = ConexionBD::obtenerInstancia()->obtenerBD()->prepare($comando);
                $sentencia->bindParam(1, $idUsuario);
                $sentencia->execute();
                $resultado = $sentencia->fetch();

                $respuesta["Saldo"] = $resultado["saldo"];
                $respuesta["SaldoRegalo"] = $resultado["saldo_regalo"];
                return ["estado" => 1, "saldo" => $respuesta];
    }

}

?>
