<?php
    include('sesion.php');
    include('Connector.php');
    $db = new Connector();

      //  OBTENER ID DE LA SUCURSAL
      $id = $_GET["id_usuario"];
      $saldo = $_POST["recarga"];
      
      // QUERY PARA ACTUALIZAR INFO
      $operacion = "UPDATE wallet SET saldo = saldo + " . $saldo . " WHERE id_usuario = " . $id ." ";
      $db->execute($operacion);

        echo "<script>
	           window.location = '../admin/admin.php'
	       </script>";         

?>