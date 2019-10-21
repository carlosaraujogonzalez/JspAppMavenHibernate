<%-- 
    Document   : Registro.jsp
    Created on : 25-jun-2018, 16:23:35
    Author     : Carlos Araújo González
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro</title>
        <link rel="stylesheet" href="styles.css">
        <script src="myscripts.js"></script>
    </head>
    <body class="registro_body">
        <div class="div_formcontainer">
            <form action="SigninServlet" method="post" class="registro_form">           
                <div class="div_registro_imgcontainer">
                    <h1>Registro</h1>
                    <img src="avatar.png" alt="Avatar" class="avatar">
                </div>
                <div class="container" id="id_registro_container">
                    <label for="registro_name"><b>Nombre</b></label>
                    <input id="id_registro_name" type="text" placeholder="Introducir nombre" name="registro_name" required>
                    <label for="registro_surname"><b>Primer apellido</b></label>
                    <input id="id_registro_surname" type="text" placeholder="Introducir primer apellido" name="registro_surname" required>
                    <label for="registro_lastname"><b>Segundo apellido</b></label>
                    <input id="id_registro_lastname" type="text" placeholder="Introducir segundo apellido" name="registro_lastname" required>
                    <label for="registro_mail"><b>e-Mail</b></label>
                    <input id="id_registro_mail" type="email" placeholder="Introducir e-mail" name="registro_mail" required><br>
                    <label for="registro_job"><b>Puesto</b></label>
                    <input id="id_registro_job" type="text" placeholder="Introducir puesto: investigador, profesor, doctor" name="registro_job">
                    <label for="registro_institucion"><b>Institución</b></label>
                    <input id="id_registro_institucion" type="text" placeholder="Introducir institución" name="registro_institucion">
                    <label for="registro_keywords"><b>Palabras clave</b></label>
                    <input id="id_registro_keywords" type="text" placeholder="Introducir palabras clave" name="registro_keywords">
                    <label for="registro_psw"><b>Contraseña</b></label>
                    <input id="id_registro_psw" type="password" placeholder="Introducir contraseña" name="registro_psw" required>
                    <button type="submit" class="accept_button">Aceptar</button>
                    <button type="reset" class="delete_button">Borrar</button>
                    <label>
                      <input type="checkbox" checked="checked" name="registro_remember">Recordarme
                    </label>
                </div>      
            </form>
        </div>
    </body>
</html>
