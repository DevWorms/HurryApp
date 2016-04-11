<?php
	
	//CLASE QUE IMPRIMIRÁ LA RESPUESTA DEL API; SI HAY UN ERROR, TAMBIÉN SE MOSTRARÁ. ESTA CLASE ES PADRE DE XML Y JSON PARA SU CONSUMO

	abstract class VistaApi	{
		
	    public $estado;
	    public abstract function imprimir($cuerpo);

	}
?>