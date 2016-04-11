<?php
    include('sesion.php');
    include('Connector.php');
    $db = new Connector();

      //  OBTENER ID DEL CLIENTE
      $id = $_GET["id"];


        $nombre = $_POST['nom_tienda'];        
        $direccion = $_POST['direccion'];

      //  QUERIES PARA ACTUALIZAR INFO
      if(!$nombre == "")  {
        $db->execute("UPDATE tienda SET nombre = '" . $nombre . "'  WHERE id_tienda = " . $id ."");
      }

      
      if(!$direccion == "")  {
        $db->execute("UPDATE tienda SET direccion = '" . $direccion . "'  WHERE id_tienda = " . $id ."");
      }
      
  
      echo "<script>
           window.location = '../admin/master.php'
       </script>";  

?>