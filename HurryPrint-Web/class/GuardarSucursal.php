<?php
 	class GuardarSucursal	{

		private $nombre;
		private $direccion;

		public function __construct($nombre,$direccion) {

			$this->conn = new Connector();

			$this->nombre = $this->conn->sec($nombre);
			$this->direccion = $this->conn->sec($direccion);
		}

		public function __destruct() {
     
			$this->conn = "";

			$this->nombre = "";
			$this->direccion = "";
    		}

		public function insert_sucursal(){
				
	        $query = "INSERT INTO tienda(nombre_tienda, direccion, id_tipo_tienda,id_cliente)
	        		VALUES ('".$this->nombre."','".$this->direccion."','1','".$_SESSION["Id"]."')";

				return $this->conn->execute($query);

		}

	}

?>