<?php
    include('sesion.php');
    include('Connector.php');
    $db = new Connector();

      //  OBTENER ID DEL CLIENTE
      $id = $_GET["id"];


        $nombre = $_POST['nom_usuario'];        
        $correo = $_POST['correo'];
        $contrasena = $_POST['contrasena'];

      //  QUERIES PARA ACTUALIZAR INFO
      if(!$nombre == "")  {
        $db->execute("UPDATE clientes SET nombre = '" . $nombre . "'  WHERE id_cliente = " . $id ."");
      }

      
      if(!$correo == "")  {
        $db->execute("UPDATE clientes SET correo = '" . $correo . "'  WHERE id_cliente = " . $id ."");
      }
      

      if(!$contrasena == "")  {
        $db->execute("UPDATE clientes SET contrasena = '" . $contrasena . "'  WHERE id_cliente = " . $id ."");
      }
  
      echo "<script>
           window.location = '../admin/master.php'
       </script>";  

?>