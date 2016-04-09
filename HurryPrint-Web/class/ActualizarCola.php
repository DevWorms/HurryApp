  <?php
    include('sesion.php');
    include('Connector.php');
    $db = new Connector();

      //  OBTENER ID DE LA SUCURSAL
      $id = $_GET["id"];

      //  SI SE SELECCIONÓ UN ARCHIVO, EJECUTA EL QUERY
      if(isset($_POST['entregado']))  {

            $entregado = $_POST['entregado'];

            $tamañoEntregado = $entregado;
            $folioDocumento = [];

            $countFolio=0;

            foreach ($entregado as $key => $entregado) {
                $folioDocumento[$countFolio] = $key;
                $countFolio++;
            }

            // QUERY PARA ACTUALIZAR INFO
            for($i=0; $i<count($tamañoEntregado); $i++)  {
                $operacion = "UPDATE documentos SET estatus = 3 WHERE folio_documento = " . $folioDocumento[$i] ."";
                $db->execute($operacion);
                eliminarDocumento($folioDocumento[$i]);
            }

              echo "<script>
      	           window.location = '../admin/sucursal.php?id_sucursal=" . $id ."'
      	       </script>";
       }

       else {
            echo "<script>
                 window.location = '../admin/sucursal.php?id_sucursal=" . $id ."'
             </script>";
         }


        //  ELIMINAR DOCUMENTO DEL FOLDER
        function eliminarDocumento($folio) {
            global $db;
            global $result;
            
            $operacion = "SELECT * FROM documentos WHERE folio_documento = " . $folio ."";
            $db->execute($operacion);
            $documento ="";

            if (!$result->num_rows == 0) {
                while ($resultado = $result->fetch_assoc()) {
                      $documento = $resultado["url_documento"];
                }
            }

            unlink($documento);


        }

         

?>