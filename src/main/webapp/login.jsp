<%-- 
    Document   : Login
    Created on : 25-jun-2018, 16:20:45
    Author     : Carlos Araújo González
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In</title>
        <link rel="stylesheet" href="styles.css">
        <script src="myscripts.js"></script>
    </head>
    <body class="login_body">
        <form action="LoginServlet">
            <div class="login_div_imgcontainer">
                <img src="avatar.png" alt="Avatar" class="avatar">
            </div>            
            <div class="login_div_container">
                <label for="uname"><b>Correo</b></label>
                <input type="text" placeholder="Introducir correo" name="mail" required>

                <label for="psw"><b>Contraseña</b></label>
                <input type="password" placeholder="Introducir contraseña" name="psw" required>

                <button type="submit" value="submit" class="login_button_ok">Ok</button>
                <button type="button" onclick="callInicioJSP()" class="login_button_cancel">Cancelar</button>
                <div>
                    <label><input type="checkbox" checked="checked" name="remember">Recordarme</label>             
                    <span class="psw"><a href="#">Olvidaste la contraseña?</a></span>
                </div>  
            </div>
        </form>     
    </body>
</html>
