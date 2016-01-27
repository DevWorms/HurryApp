function postform() {}

function form1() {

    var nombre = $("#name").val();
    var correo = $("#mail").val();
    var contrasena = $("#pass").val();

    var phpPost = "nombre=" + nombre + "&correo=" + correo + "&contrasena=" + contrasena;

    $.ajax({scriptCharset: "utf-8",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        cache: false,
        type: "POST",
        data: phpPost + "&ID_pantalla=01",
        dataType: "text",
        url: "class/PostInfo.php",
        success: function (info) {
            if (info == "1") {
                $("#btnSiguiente").show();
                notifyMe();
                $("#btn_form1").hide();
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function notifyMe(phpPost) {

    // Comprobar que el browser soporta notificaciones.
    if (!("Notification" in window)) {
        alert("This browser does not support desktop notification");
    }

    // Comprobar si el usuario acepta notificaciones
    else if (Notification.permission === "granted") {
    // Si acepta, se crea la notificación;
        var notification = new Notification(phpPost);
    }

      // Si no acepta, debemos pedirle permiso para su uso
    else if (Notification.permission !== 'denied') {
        Notification.requestPermission(function (permission) {
            // Si acepta, se crea la notificación;
            if (permission === "granted") {
                var notification = new Notification(phpPost);
            }
        });
    }
}


function validateInfo() {

    var sucursal = $("#sucursal").val();
    var hojas = $("#hojas").val();
    var intervalo = $("#intervalo").val();

    if ($("#blanconegro").is(":checked")) {
        var bn = 1;
    } else {
        var bn = 0;
    }

    if ($("#color").is(":checked")) {
        var color = 1;
    } else {
        var color = 0;
    }

    if ($("#caratula").is(":checked")) {
        var cara = 1;
    } else {
        var cara = 0;
    }

    if ($("#lados").is(":checked")) {
        var lados = 1;
    } else {
        var lados = 0;
    }

    if ($("#carta").is(":checked")) {
        var carta = 1;
    } else {
        var carta = 0;
    }

    if ($("#oficio").is(":checked")) {
        var oficio = 1;
    } else {
        var oficio = 0;
    }

    var post =

    "sucursal=" + sucursal + "&hojas=" + hojas + "&intervalo=" + intervalo +
    "&blanco_negro=" + bn + "&color=" + color + "&caratula=" + cara +  
    "&lados=" + lados + "&carta=" + carta + "&oficio=" + oficio; 

    console.log(post);
}


$(document).ready(function () {

    //pantalla 01
    $("#btn_form1").click(function () {
        validateInfo();        
    }); 

});

    //**** FUNCIONES DE VALIDACION DE CAMPOS ******

    $.fn.numerosDecimales = function(){
    $(this).keypress(function(tecla){
         if(tecla.charCode >= 48 && tecla.charCode <= 57 || tecla.charCode == 0 || tecla.charCode == 46 ) {
            return true;
        }
        else{
            return false;
        }
    });
    };

    $.fn.numerosEnteros = function(){
    $(this).keypress(function(tecla){
         if(tecla.charCode >= 48 && tecla.charCode <= 57 || tecla.charCode == 0 ) {
            return true;
        }
        else{
            return false;
        }
    });
    };

$.fn.soloLetrasyEspacio = function(){
    $(this).keypress(function(tecla){
         if(tecla.charCode >= 65 & tecla.charCode <= 90 || tecla.charCode >= 97 & tecla.charCode <= 122 || tecla.charCode == 32  || tecla.charCode == 0) {
            return true;
        }
        else{
            return false;
        }
    });
};

$.fn.rangoPorcentaje=function(){ // VALIDACION PORCENTAJE DE  0 A 100
    $.caja = $(this);
    $(this).keyup(function(tecla){
        if(parseFloat($.caja.val()) < 0 || parseFloat($.caja.val()) > 100 ){
            $.caja.val("");
        }
    });
};

$.fn.noEmpty = function(){
    var valida = 0;
    var last_index=0;

    $.formulario = $(this).parents(".step").children(".row").children("div").children("input,textarea");
    $.formulario.each(function(indice,elemento){
        if($(elemento).val() != ""){
            valida++;
        }
        last_index = indice;
    });
    if(valida == (last_index + 1)){
       return true;
    }
    else{
        alert("No puede haber campos vacios")
       return false;
    }
};

    // ejemplo de validaciones
    /* Estas funciones son bajo keypress asi que borrara todo lo que no sea permitido en automatico
        $("#id").soloLetrasyEspacio();
        $("#id").soloEnteros();
        $("#id").numerosDecimales();
        $("#id").rangoPorcentaje();
        La funcion que sigue valida todos los input y text area que esten detro de div.step well > div.row > div > input,textarea
        tengo entendio que todos pusiero nsus forms en esa estructura asi que debe servir para todos y regresa un booleano,como
        ven funcina a partir de selccionar el boton del div donde estemos ya que de ahi va seleccionando su padre y este a lso input, se usa asi
        $("#btnDefinirProy").click(function () {
            if($("#btnDefinirProy").noEmpty()){
                defeinicionProyecto01();
            }
        });

         esta es importante por que por l oque veo la BD permite nulos y se peude neviuar un formulario totalmente vacio
    */
