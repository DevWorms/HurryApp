<?php

    //     ESTA CLASE IMPRIMIRÁ LOS RESULTADOS OBTENIDOS DE LA API EN UN JSON 

    require_once "VistaApi.php";

    class VistaJson extends VistaApi    {

        public function imprimir($cuerpo)   {

            if ($this->estado) {
                http_response_code($this->estado);

            }

            header('Content-Type: application/json; charset=utf8');
            echo json_encode($cuerpo, JSON_PRETTY_PRINT);
            exit;
        }
    }

?>