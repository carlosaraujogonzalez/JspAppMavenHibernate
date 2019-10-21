/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//////////////////////////
// Funciones index.html //
//////////////////////////
function callInicioJSP() {
    window.location.href = "inicio.jsp";
}

//////////////////////////
// Funciones inicio.jsp //
//////////////////////////
function callDatosPersonalesJSP() {
    window.location.href = "datosPersonales.jsp";
}

///////////////////////////////////
// Funciones DatosPersonales.jsp //
///////////////////////////////////
function callLoginJSP() {
    window.location.href = "login.jsp";
}

function callSalir() {
    window.location.href = "salir.jsp";
}

function callSigninJSP() {
    window.location.href = "registro.jsp";
}

///////////////////////////////////////
////// Funciones publicaciones.jsp ////
///////////////////////////////////////
function modal() {

    // Get the modal
    var modal = document.getElementById('publishes_modal');

    // Get the button that opens the modal
    var btn_add = document.getElementById("publishes_button_add");
    var btn_update = document.getElementById("publishes_button_update");
    var btn_delete = document.getElementById("publishes_button_delete");

    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks on the button, open the modal
    btn_add.onclick = function () {
        document.getElementById("publishes_h2").innerHTML = "Añadir publicación";
        modal.style.display = "block";
        document.getElementById("publishes_button_ok").innerHTML = "Añadir";
        document.getElementById("id_publicaciones_input").value = "add";
    };

    btn_update.onclick = function () {
        document.getElementById("publishes_h2").innerHTML = "Modificar publicación";
        modal.style.display = "block";
        document.getElementById("publishes_button_ok").innerHTML = "Modificar";
        document.getElementById("id_publicaciones_input").value = "update";
    };

    btn_delete.onclick = function () {
        document.getElementById("publishes_h2").innerHTML = "Eliminar publicación";
        modal.style.display = "block";
        document.getElementById("publishes_button_ok").innerHTML = "Eliminar";
        document.getElementById("id_publicaciones_input").value = "delete";
    };

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        modal.style.display = "none";
    };

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };
}


function setValueInputLogin() {
    // get input
    document.getElementById("datosPersonales_input").innerHTML = "login";
}

function setValueInputSignin() {
    // get input
    document.getElementById("datosPersonales_input").innerHTML = "signin";
}


function openCreateSessionJsp() {
    window.location.href = "login.jsp";
}

function openRegistroJsp() {
    window.location.href = "registro.jsp";
}
//////////////////////////////////////
/// Funciones registro.jsp //////////
//////////////////////////////////////

//////////////////////////////////////
/// Funciones resultado.jsp //////////
//////////////////////////////////////

//////////////////////////////////////
/// Funciones error.jsp //////////
//////////////////////////////////////





