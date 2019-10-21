<%--
    Document   : Salir
    Created on : 04-jul-2018, 16:13:21
    Author     : pc6
--%>

<%@page import="Utilities.HibernateUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hasta luego!</h1>
        <%
            HibernateUtil.closeSessionFactory();
        %>
    </body>
</html>
