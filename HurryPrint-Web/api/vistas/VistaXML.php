<?php

    //     ESTA CLASE IMPRIMIRÁ LOS RESULTADOS OBTENIDOS DE LA API EN UN XML

    require_once "VistaApi.php";

    
    class VistaXML extends VistaApi     {

        public function imprimir($cuerpo)   {

            if ($this->estado)  {
                http_response_code($this->estado);

            }

            header('Content-Type: text/xml');

            $xml = new SimpleXMLElement('<respuesta/>');
            self::parsearArreglo($cuerpo, $xml);
            print $xml->asXML();

            exit;
        }

        //  CONVIERTE EL ARRAY EN XML

        public function parsearArreglo($data, &$xml_data)
        {
            foreach ($data as $key => $value) {

                if (is_array($value)) {                    
                    if (is_numeric($key)) {
                        $key = 'item' . $key;

                    }

                    $subnode = $xml_data->addChild($key);
                    self::parsearArreglo($value, $subnode);

                } else {
                    $xml_data->addChild("$key", htmlspecialchars("$value"));

                }

            }
        }
    }

?>