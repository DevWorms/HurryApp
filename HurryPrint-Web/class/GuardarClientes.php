<?php
 	class GuardarClientes	{

		private $nombre;
		private $correo;
		private $contrasena;

		public function __construct($nombre,$correo,$contrasena) {

			$this->conn = new Connector();

			$this->nombre = $this->conn->sec($nombre);
			$this->correo = $this->conn->sec($correo);
			$this->contrasena = $this->conn->sec($contrasena);
		}

		public function __destruct() {
     
			$this->conn = "";

			$this->nombre = "";
			$this->correo = "";
			$this->contrasena = "";
    		}

		public function insert_clientes(){
				
	        $query = "INSERT INTO clientes(nombre, correo, contrasena)
	        		VALUES ('".$this->nombre."','".$this->correo."','".$this->contrasena."')";

				return $this->conn->execute($query);

		}

	}

?>