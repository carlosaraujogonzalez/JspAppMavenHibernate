<%--
    Document   : inicio
    Created on : 25-jun-2018, 11:47:59
    Author     : Carlos Araújo
--%>

<%@page import="Utilities.HibernateUtil"%>
<%@page import="Controlador.DBController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles.css">
        <title>Inicio</title>
        <script src="myscripts.js"></script>
    </head>
    <body>
        <div class="inicio_imgcontainer">
            <img src="https://media.giphy.com/media/3ornk7TgUdhjhTYgta/giphy.gif" alt="goodLuck" class="center">
        </div>
        <%
            HibernateUtil.buildSessionFactory();
        %>
        <div class="inicio_buttoncontainer">
            <button onclick="callDatosPersonalesJSP()">Comenzar</button>
            <button onclick="callSalir()">Salir</button>
        </div>
    </body>
</html>

