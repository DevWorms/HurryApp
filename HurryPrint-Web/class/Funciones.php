<?php
	require ('Connector.php');	
	$db = new Connector();

	function MostrarUsuarios() {
		global $db;
		global $result;
		$master = "";

		if ($db->execute("SELECT * FROM  clientes;")) {

                        if ($result->num_rows == 0) {
                            echo "No hay usuarios registrados.";
                        } else {
                            while ($resultado = $result->fetch_assoc()) {

                            		if($resultado["master"] == "1")   {
                            			$master = "Si";
                                                $disabled = "disabled";
                                            }
                            		else  {
                            			$master = "No";
                                                $disabled = "";
                                            }


            			echo  '
                                       <div class="well"  style="text-align:center">     
	                                    	<table class="table table-hover"> 
	                                    		<thead>
		                                    		<tr>
		                                            		<th style="text-align:center">Nombre</th>
		                                            		<th style="text-align:center">Correo</th>
			                                            	<th style="text-align:center">Contraseña</th>
			                                            	<th style="text-align:center">Web Master</th>
			                                            	<th style="text-align:center">Modificar</th>			                                            	
			                                            	<th style="text-align:center">Eliminar</th>
		                                    		</tr>
	                                    		</thead>
	    
		                                    <tbody>
		                                        	<tr>
			                                            	<td>' . $resultado["nombre"] . '</td>
			                                            	<td>' . $resultado["correo"] . '</td>
			                                            	<td>' . $resultado["contrasena"] . '</td>
			                                            	<td>' . $master . '</td>
			                                            	<td><button class="btn btn-warning" id="btn_usuario" data-toggle="modal" data-target="#modalUpdate" onclick="formUsuario(' . $resultado["id_cliente"] .');"> O </button></td>
			                                            	<td><button onclick="eliminarUsuario(' . $resultado["id_cliente"] . ')" class="btn btn-danger" id="btn_usuario" ' . $disabled . ' > X </button></td>
		                                        	</tr>
		                                    </tbody>
	                                	</table>
	                                	
                        		</div>
                                	';
                            }
                        }
                    } else {
                        echo '<tr><th>Ocurrio un error al ejecutar la consulta. Intente nuevamente.<th></tr>';
                    }
			 
	}

	function MostrarSucursales() {
		global $db;
		global $result;

		if ($db->execute("SELECT * FROM  tienda;")) {

                        if ($result->num_rows == 0) {
                            echo "No hay sucursales registradas.";
                        } else {
                            while ($resultado = $result->fetch_assoc()) {

                            		if($resultado["direccion"] == " ")
                            			$direccion = "-";
                            		else
                            			$direccion = $resultado["direccion"];

            			echo  '
                                       <div class="well"  style="text-align:center">     
	                                    	<table class="table table-hover"> 
	                                    		<thead  >
		                                    		<tr>
		                                            		<th style="text-align:center">Nombre de Sucursal</th>
		                                            		<th style="text-align:center">Direccion</th>
			                                            	<th style="text-align:center">Modificar</th>			                                            	
			                                            	<th style="text-align:center">Eliminar</th>
		                                    		</tr>
	                                    		</thead>
	    
		                                    <tbody>
		                                        	<tr>
			                                            	<td>' . $resultado["nombre_tienda"] . '</td>
			                                            	<td>' . $direccion . '</td>
			                                            	<td><button class="btn btn-warning" id="btn_sucursal" data-toggle="modal" data-target="#modalUpdateSucursal" onclick="formSucursal(' . $resultado["id_tienda"] .');"> O </button></td>
			                                            	<td><button onclick="eliminarSucursal(' . $resultado["id_tienda"] . ')" class="btn btn-danger" id="btn_sucursal" > X </button></td>
		                                        	</tr>
		                                    </tbody>
	                                	</table>
	                                	
                        		</div>
                                	';
                            }
                        }
                    } else {
                        echo '<tr><th>Ocurrio un error al ejecutar la consulta. Intente nuevamente.<th></tr>';
                    }
			 
	}

           function ListarSucursales() {
                global $db;
                global $result;

                if ($db->execute("SELECT * FROM  tienda;")) {

                                if ($result->num_rows == 0) {
                                    echo "No hay sucursales registradas.";
                                } else {
                                    while ($resultado = $result->fetch_assoc()) {

                                            if($resultado["direccion"] == " ")
                                                $direccion = "-";
                                            else
                                                $direccion = $resultado["direccion"];

                                echo  '
                                               <li><i>' . $resultado["nombre_tienda"] . '
                                                <ul>
                                                    <li>' . $resultado["direccion"] . '</li>
                                                </ul>

                                               </i></li>

                                            ';
                                    }
                                }
                            } else {
                                echo '<tr><th>Ocurrio un error al ejecutar la consulta. Intente nuevamente.<th></tr>';
                            }
                     
            }     

	function SeleccionarSucursales() {
		global $db;
		global $result;

		if ($db->execute("SELECT * FROM  tienda;")) {

                        if ($result->num_rows == 0) {
                            echo "No hay sucursales registradas.";
                        } else {
                            while ($resultado = $result->fetch_assoc()) {

                            		if($resultado["direccion"] == " ")
                            			$direccion = "-";
                            		else
                            			$direccion = $resultado["direccion"];

            			echo  '
                                       <div class="well"  style="text-align:center">     
	                                    	<table class="table table-hover"> 
	                                    		<thead  >
		                                    		<tr>
		                                            		<th style="text-align:center">No. Sucursal</th>
		                                            		<th style="text-align:center">Nombre de Sucursal</th>
		                                            		<th style="text-align:center">Dirección</th>		                                            	
			                                            	<th style="text-align:center">Seleccionar</th>
		                                    		</tr>
	                                    		</thead>
	    
		                                    <tbody>
		                                        	<tr>
			                                            	<td>' . $resultado["id_tienda"] . '</td>
			                                            	<td>' . $resultado["nombre_tienda"] . '</td>
			                                            	<td>' . $direccion . '</td>			                                            	
			                                            	<td><a href="sucursal.php?id_sucursal=' . $resultado["id_tienda"] . '"  class="btn btn-success" id="btn_seleccionar">Ir</a></td>
		                                        	</tr>
		                                    </tbody>
	                                	</table>
	                                	
                        		</div>
                                	';
                            }
                        }
                    } else {
                        echo '<tr><th>Ocurrio un error al ejecutar la consulta. Intente nuevamente.<th></tr>';
                    }
			 
	}


	function InfoSucursales($sucursal) {
		global $db;
		global $result;

		$db->execute("SELECT * FROM  tienda WHERE id_tienda =" . $sucursal . " ;");

                    if (!$result->num_rows == 0) {
                        while ($resultado = $result->fetch_assoc()) {

                		echo $resultado["nombre_tienda"];
                        }
                    }
	}


	function ObtenerSaldos($id_usuario, $seleccion) {
		global $db;
		global $result;

		$db->execute("SELECT * FROM wallet WHERE id_usuario =" . $id_usuario . " ;");

                    if (!$result->num_rows == 0) {

                        while ($resultado = $result->fetch_assoc()) {

                        	if($seleccion == "saldo")
                			echo $resultado["saldo"];

                		else if($seleccion == "regalo")
                			echo $resultado["saldo_regalo"];

                		else if($seleccion == "funcion")
                			echo $resultado["saldo"] + $resultado["saldo_regalo"];

                        }

                    }
	}


	function ImpresionSucursal() {
		global $db;
		global $result;
               $counter = 1;

		$db->execute("SELECT a.id_tienda, a.nombre_tienda, a.direccion, a.lat, a.lng, b.abierto, b.color, b.blanco_negro FROM tienda a inner join estatus_tienda b on a.id_tienda = b.id_tienda ORDER BY a.id_tienda ASC");

                    if (!$result->num_rows == 0) {
                        while ($resultado = $result->fetch_assoc()) {
                            if($counter == 1)
                                $checked = "checked";
                            else
                                $checked = "";

                            $abierto = $resultado["abierto"];
                            $bn = $resultado["blanco_negro"];
                            $color = $resultado["color"];

                            $no = '<i class="fa fa-times" style="color:red"></i>';
                            $yes = '<i class="fa fa-check" style="color:green"></i>';

                            if($abierto)
                                $abierto = $yes;
                            else 
                                $abierto = $no . '<p style="font-size:10px">Recoger mañana al abrir sucursal</p>';

                            if($bn)
                                $bn = $yes;
                            else 
                                $bn = $no;

                            if($color)
                                $color = $yes;
                            else 
                                $color = $no;

                		echo '
                                  <tr>
                                    <td style="word-wrap:break-word;"> <input type="radio" name="sucursal" value= "' . $resultado["id_tienda"] . '" ' . $checked . '> '
                                     . $resultado["nombre_tienda"] . '<br>
                                     <i>
                                     '. $resultado["direccion"] .'
                                     </i>
                                     </td>

                                    <td align="center"> ' . $abierto . '</td>
                                    <td align="center"> ' . $bn . '</td>
                                    <td align="center"> ' . $color . ' </td>
                                  
                                  </tr>
                		';

                            $counter++;
                        }
                    }
	}


	function ColaImpresion($idsucursal) {
		global $db;
		global $result;

		$db->execute("SELECT a.folio_documento, a.nombre_documento, a.url_documento, a.ver, a.numero_hojas, a.rango_hojas, a.blanco_negro, a.color, a.caratula_color, a.ambos_lados, a.carta, a.oficio, c.descripcion FROM documentos a inner join operacion b on a.id_operacion = b.id_operacion inner join estatus c on c.id_estatus = a.estatus where activo = false and estatus = 1 and b.id_tienda=".$idsucursal." order by b.fecha_operacion desc");

                    if (!$result->num_rows == 0) {
                        while ($resultado = $result->fetch_assoc()) {

                        	$bn = $resultado["blanco_negro"];
                        	$color = $resultado["color"];
                        	$cara = $resultado["caratula_color"];
                        	$lados = $resultado["ambos_lados"];
                        	$carta = $resultado["carta"];
                        	$oficio = $resultado["oficio"];

                        	$no = '<i class="fa fa-times" style="color:red"></i>';
                        	$yes = '<i class="fa fa-check" style="color:green"></i>';

                        	if($bn == 0)
                        		$bn = $no;
                        	else 
                        		$bn = $yes;


                        	if($color == 0)
                        		$color = $no;
                        	else 
                        		$color = $yes;

                        	if($cara == 0)
                        		$cara = $no;
                        	else 
                        		$cara = $yes;
                        	

                        	if($lados == 0)
                        		$lados = $no;
                        	else 
                        		$lados = $yes;

                        	if($carta == 0)
                        		$carta = $no;
                        	else 
                        		$carta = $yes;
                        	

                        	if($oficio == 0)
                        		$oficio = $no;
                        	else 
                        		$oficio = $yes;



                		echo  '
    
	                                    <tbody>
	                                        	<tr>
		                                            	<td>' . $resultado["folio_documento"] . '</td>
		                                            	<td>' . $resultado["nombre_documento"] . '</td>
		                                            	<td><a href= ' . $resultado["ver"] . ' target="_blank" id="ver"> <i class="fa fa-file-text"></i> </a></td>
		                                            	<td>' . $resultado["numero_hojas"] . '</td>
		                                            	<td>' . $resultado["rango_hojas"] . '</td>
		                                            	<td>' . $bn . '</td>
		                                            	<td>' . $color . '</td>
		                                            	<td>' . $cara . '</td>
		                                            	<td>' . $lados . '</td>
		                                            	<td>' . $carta . '</td>
		                                            	<td>' . $oficio. '</td>
		                                            	<td>' . $resultado["descripcion"] . '</td>
		                                            	<td><input type="checkbox" id="' . $resultado["folio_documento"] . '" name ="entregado[' . $resultado["folio_documento"] . ']"></td>	                                            	
		                                            	
	                                            	</tr>
	                                    </tbody>
                                	';
                        }
                    }
	}

	function EntregadosImpresion($idsucursal) {
		global $db;
		global $result;

		$db->execute("SELECT a.folio_documento, a.nombre_documento, a.url_documento, a.numero_hojas, a.rango_hojas, a.blanco_negro, a.color, a.caratula_color, a.ambos_lados, a.carta, a.oficio, c.descripcion FROM documentos a inner join operacion b on a.id_operacion = b.id_operacion inner join estatus c on c.id_estatus = a.estatus where activo = false and estatus = 3 and b.id_tienda=".$idsucursal." order by b.fecha_operacion desc");

                    if (!$result->num_rows == 0) {
                        while ($resultado = $result->fetch_assoc()) {

                        	$bn = $resultado["blanco_negro"];
                        	$color = $resultado["color"];
                        	$cara = $resultado["caratula_color"];
                        	$lados = $resultado["ambos_lados"];
                        	$carta = $resultado["carta"];
                        	$oficio = $resultado["oficio"];

                        	$no = '<i class="fa fa-times" style="color:red"></i>';
                        	$yes = '<i class="fa fa-check" style="color:green"></i>';

                        	if($bn == 0)
                        		$bn = $no;
                        	else 
                        		$bn = $yes;


                        	if($color == 0)
                        		$color = $no;
                        	else 
                        		$color = $yes;

                        	if($cara == 0)
                        		$cara = $no;
                        	else 
                        		$cara = $yes;
                        	

                        	if($lados == 0)
                        		$lados = $no;
                        	else 
                        		$lados = $yes;

                        	if($carta == 0)
                        		$carta = $no;
                        	else 
                        		$carta = $yes;
                        	

                        	if($oficio == 0)
                        		$oficio = $no;
                        	else 
                        		$oficio = $yes;



                		echo  '
    
	                                    <tbody>
	                                        	<tr>
		                                            	<td>' . $resultado["folio_documento"] . '</td>
		                                            	<td>' . $resultado["nombre_documento"] . '</td>
		                                            	<td>' . $resultado["numero_hojas"] . '</td>
		                                            	<td>' . $resultado["rango_hojas"] . '</td>
		                                            	<td>' . $bn . '</td>
		                                            	<td>' . $color . '</td>
		                                            	<td>' . $cara . '</td>
		                                            	<td>' . $lados . '</td>
		                                            	<td>' . $carta . '</td>
		                                            	<td>' . $oficio. '</td>
		                                            	
	                                            	</tr>
	                                    </tbody>
                                	';
                        }
                    }
	}

	function ObtenerStatus() {
		global $db;
		global $result;

		$db->execute("SELECT * FROM estatus WHERE id_tipo_tienda = 1;");

                    if (!$result->num_rows == 0) {
                        while ($resultado = $result->fetch_assoc()) {

                		echo '
				<option value= "' . $resultado["id_estatus"] . '">' . $resultado["descripcion"] . '</option>
                		';
                        }
                    }
	}

	function SucursalStatus($idsucursal) {
		global $db;
		global $result;

                $db->execute("SELECT * FROM estatus_tienda WHERE id_tienda =" . $idsucursal . " ;");

                if (!$result->num_rows == 0) {
                        while ($resultado = $result->fetch_assoc()) {                            
                            $resAbierto = $resultado["abierto"];
                            $resColor = $resultado["color"];
                            $resBN = $resultado["blanco_negro"];
                        }
                    }

                if($resAbierto == 1)
                    $checkAbierto = "checked";
                else
                    $checkAbierto = "";

                if($resColor == 1)
                    $checkColor = "checked";
                else
                    $checkColor = "";

                if($resBN == 1)
                    $checkBN = "checked";
                else
                    $checkBN = "";


		$checked = "checked";

		echo  '    			
	                        <input type="checkbox" id="abierto" name="abierto" ' . $checkAbierto . '> Abierto  <br>                    
	                        <input type="checkbox" id="color" name="color" ' . $checkColor . '> Impresiones a Color  <br>
	                        <input type="checkbox" id="blanconegro" name="blanconegro" ' . $checkBN . '> Impresiones Blanco y Negro  <br>                                 
                            	';		
	}  

	function ObtenerFechaHoy()	{		
		date_default_timezone_set('America/Mexico_City');

		$dias = array("Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sábado");
		$meses = array("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre");

		$min_seg = getdate(date("U"));
		$hora = $min_seg[hours];
		$minuto = $min_seg[minutes];
		$am_pm = "";

		if($hora < 10)
			$hora = "0".$hora;
		if($minuto < 10)
			$minuto = "0".$minuto;

		if($hora >=0  && $hora <= 12 )
			$am_pm = "am";
		else
			$am_pm = "pm";
 
		echo $dias[date('w')]." ".date('d')." de ".$meses[date('n')-1]. " del ".date('Y') . " a las " . $hora .":" . $minuto . $am_pm ;

	}

          function ObtenerHistorialUsuario($id){
            global $db;
            global $result;
            $db->execute("CALL consultar_historial_usuario(". $id .")"); 

            if (!$result->num_rows == 0) {
                        while ($resultado = $result->fetch_assoc()) {

                            if($resultado["descripcion"] == "esperando")
                                $statusDoc = '<td style="color: orange; font-size:18px;"><strong> Esperando <i class="fa fa-clock-o"></i></strong></td>';
                            else
                                $statusDoc = '<td style="color: green"><strong> Entregado <i class="fa fa-check-circle-o"></i></strong></td>';

                                echo '
                                        <tr>
                                            <td>' . $resultado["folio_documento"] . '</td>
                                            <td>' . $resultado["nombre_documento"] . '</td>
                                            <td> $' . $resultado["costo"] . '</td>
                                            <td>' . $resultado["fecha"] . '</td>
                                            <td>' . $resultado["nombre_tienda"] . '</td>'
                                            . $statusDoc . '
                                        </tr>
                                ';
                        }
                    }
        }

        function EstadoCuenta($id){
            global $db;
            global $result;
            $db->execute("CALL consultar_estado_cuenta_cliente(". $id .")"); 

            if (!$result->num_rows == 0) {
                        while ($resultado = $result->fetch_assoc()) {

                                echo '
                                        <tr>
                                            <td> $' . $resultado["ventas"] . '</td>
                                            <td> $' . $resultado["comisiones"] . '</td>
                                            <td> $' . $resultado["ganancia neta"] . '</td>
                                            <td>' . $resultado["mes"] . '</td>
                                        </tr>
                                ';
                        }
                    }
        }
?>
