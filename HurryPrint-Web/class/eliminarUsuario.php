  <?php
    include('sesion.php');
    include('Connector.php');
    $db = new Connector();

      //  OBTENER ID DEL USUARIO
      $id = $_GET["id"];

       // QUERY PARA ELIMINAR SUCURSAL
        $operacion = "DELETE FROM clientes WHERE id_cliente = " . $id ."";
        $db->execute($operacion);
    
      echo "<script>
           window.location = '../admin/master.php'
       </script>";
?>