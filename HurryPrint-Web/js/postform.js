function Sucursal() {

    var nombre = $("#nom_tienda").val();
    var direccion = $("#direccion").val();

    var phpPost = "nombre=" + nombre + "&direccion=" + direccion;

    $.ajax({scriptCharset: "utf-8",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        cache: false,
        type: "POST",
        data: phpPost + "&ID_pantalla=01",
        dataType: "text",
        url: "../class/PostInfo.php",
        success: function (info) {
            if (info == "1") {
                notifyMe("Se añadió la sucursal " + nombre);
                location.reload();
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function Cliente() {

    var nombre = $("#nom_usuario").val();
    var correo = $("#correo").val();
    var contrasena = $("#contrasena").val();

    var phpPost = "nombre=" + nombre + "&correo=" + correo + "&contrasena=" + contrasena;

    $.ajax({scriptCharset: "utf-8",
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        cache: false,
        type: "POST",
        data: phpPost + "&ID_pantalla=02",
        dataType: "text",
        url: "../class/PostInfo.php",
        success: function (info) {
            if (info == "1") {
                notifyMe("Se añadió el usuario " + nombre);
                location.reload();
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

$(document).ready(function () {

    //pantalla Sucursal
    $("#btn_sucursal").click(function () {
        Sucursal();        
    }); 

    //pantalla Cliente
    $("#btn_cliente").click(function () {
        Cliente();        
    }); 

});