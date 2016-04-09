$(document).ready(function () {

	//	RESTRICCIONES DE BLANCO Y NEGRO
	$("#blanconegro").click(function(){
	    $("#color").prop("checked",false);
	});
	
	//	RESTRICCIONES DE COLOR
	$("#color").click(function(){
	    $("#blanconegro").prop("checked",false);
	    $("#caratula").prop("checked",false);
	});
	
	//	RESTRICCIONES DE CARATULA
	$("#caratula").click(function(){
	    $("#blanconegro").prop("checked",true);
	    $("#color").prop("checked",false);
	});
	
	//	RESTRICCIONES DE CARTA
	$("#carta").click(function(){
	    $("#oficio").prop("checked",false);
	});
	
	//	RESTRICCIONES DE OFICIO
	$("#oficio").click(function(){
	    $("#carta").prop("checked",false);
	});

});