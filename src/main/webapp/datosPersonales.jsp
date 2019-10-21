<%-- 
    Document   : DatosPersonales
    Created on : 25-jun-2018, 16:20:24
    Author     : Carlos Araújo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Datos Personales</title>
        <script src="myscripts.js"></script>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>      
        <div class="datosPersonales_container">
            <div class="datosPersonales_imgcontainer">
                <img src="https://media.giphy.com/media/ASd0Ukj0y3qMM/giphy.gif" alt="welcome" class="center">
            </div>
            <div class="datosPersonales_buttonscontainer">               
                <input type="hidden" id="datosPersonales_input" name="datosPersonales_input">
                <button type="button" onclick="callLoginJSP()" class="datosPersonales_buttons">Entrar</button>
                <button type="button" onclick="callSigninJSP()" class="datosPersonales_buttons">Registrarse</button>             
            </div>
        </div>
    </body>
</html>
