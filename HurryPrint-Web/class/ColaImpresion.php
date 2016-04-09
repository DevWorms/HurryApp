<?php
	include('Connector.php');
	$db = new Connector();
	
	$status = ($_GET['status']);
	$tienda = ($_GET['tienda']);

	$db->execute("SELECT a.folio_documento, a.nombre_documento, a.url_documento, a.numero_hojas, a.rango_hojas, a.blanco_negro, a.color, a.caratula_color, a.ambos_lados, a.carta, a.oficio, c.descripcion FROM documentos a inner join operacion b on a.id_operacion = b.id_operacion inner join estatus c on c.id_estatus = a.estatus where activo = false and estatus =".$status." and b.id_tienda=".$tienda." order by b.fecha_operacion desc");
	//$query = "SELECT * FROM usuario WHERE id_usuario = '".$status."'";
	//$db->execute($query);


	echo 
	        '<table class="table table-hover"> 
	            <thead  >
	                <tr>
	                        <th style="text-align:center">Folio</th>
	                        <th style="text-align:center">Nombre</th>
	                        <th style="text-align:center">Ver</th>                                                      
	                        <th style="text-align:center">No. Hojas</th>
	                        <th style="text-align:center">Rango de Hojas</th>
	                        <th style="text-align:center">Blanco y Negro</th>                                                       
	                        <th style="text-align:center">Color</th>                                                        
	                        <th style="text-align:center">Caratula a Color</th>                                                     
	                        <th style="text-align:center">Ambos Lados</th>                                                      
	                        <th style="text-align:center">Carta</th>                                                        
	                        <th style="text-align:center">Oficio</th>        
	                        <th style="text-align:center">Status</th>
	                </tr>
	            </thead>' . $status;

		if ($result->num_rows == 1) {
                        while ($resultado = $result->fetch_assoc()) {

                		echo  '
    
	                                    <tbody>
	                                        	<tr>
		                                            	<td>' . $resultado["folio_documento"] . '</td>
		                                            	<td>' . $resultado["nombre_documento"] . '</td>
		                                            	<td><a href= ' . $resultado["url_documento"] . ' "> Ver </a></td>
		                                            	<td>' . $resultado["numero_hojas"] . '</td>
		                                            	<td>' . $resultado["rango_hojas"] . '</td>
		                                            	<td>' . $resultado["blanco_negro"] . '</td>
		                                            	<td>' . $resultado["color"] . '</td>
		                                            	<td>' . $resultado["caratula_color"] . '</td>
		                                            	<td>' . $resultado["ambos_lados"] . '</td>
		                                            	<td>' . $resultado["carta"] . '</td>
		                                            	<td>' . $resultado["oficio"] . '</td>
		                                            	<td>' . $resultado["descripcion"] . '</td>
		                                            	
	                                            	</tr>
	                                    </tbody>
                                	';
                        }
                    }	

        	echo "</table>";
?>