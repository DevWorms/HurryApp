  <?php
    include('sesion.php');
    include('Connector.php');
    $db = new Connector();

      //  OBTENER ID DE LA SUCURSAL
      $id = $_GET["id"];

      if (isset($_POST['abierto']))   {
                  $abierto = "1";
          } else {
                  $abierto = "0";
          }

      if (isset($_POST['color'])) {
              $color = "1";
      } else {
              $color = "0";
      }

      if (isset($_POST['blanconegro']))  {
              $blanconegro = "1";
      } else {
              $blanconegro = "0";
      }

      $db->execute("CALL crear_nuevo_estatus_tienda(" . $id . " , " . $abierto . " , " . $color . " , " . $blanconegro . ");");

      echo "<script>
           window.location = '../admin/sucursal.php?id_sucursal=" . $id ."'
       </script>";
?>