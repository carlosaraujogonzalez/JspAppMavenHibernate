<%-- 
    Document   : error
    Created on : 29-jun-2018, 10:30:07
    Author     : Carlos Araújo González
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <h1 id="id_error_h1">Error: <%= request.getAttribute("error") %></h1>
    </body>
</html>
