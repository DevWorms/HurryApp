  <?php
    include('sesion.php');
    include('Connector.php');
    $db = new Connector();

      //  OBTENER ID DE LA SUCURSAL
      $id = $_GET["id"];

       // QUERY PARA ELIMINAR SUCURSAL
        $operacion = "DELETE FROM tienda WHERE id_tienda = " . $id ."";
        $db->execute($operacion);
    
      echo "<script>
           window.location = '../admin/master.php'
       </script>";

?>