<%-- 
    Document   : resultado
    Created on : 28-jun-2018, 16:20:11
    Author     : pc6
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado</title>
        <link rel="stylesheet" href="styles.css">
        <script src="myscripts.js"></script>
    </head>
    <body>       
        <% if (request.getAttribute("type").equals("publish")){ %>
            <div id="id_resultado_publicacion">
                <h1 class="resultado_h1">Mostrar Info Publicación</h1>
                <h2 class="resultado_h2">Título: <%= request.getAttribute("title")%></h2>
                <h2 class="resultado_h2">Año: <%= request.getAttribute("year")%></h2>
                <h2 class="resultado_h2">Primera página: <%= request.getAttribute("first_page")%></h2>
                <h2 class="resultado_h2">Última página: <%= request.getAttribute("last_page")%></h2>
                <h2 class="resultado_h2">ID autor de contacto: <%= request.getAttribute("id_autor_contacto")%></h2>
            </div>
        <%}%>
        <% if (request.getAttribute("type").equals("researcher")){ %>
            <div id="id_resultado_investigador">
                <h1 class="resultado_h1"><%= request.getAttribute("text") %></h1>            
                <h1 class="resultado_h1">Mostrar Info Investigador</h1>
                <h2 class="resultado_h2">Nombre: <%= request.getAttribute("registro_name")%></h2>
                <h2 class="resultado_h2">Apellidos: <%= request.getAttribute("registro_surname")+" "+request.getAttribute("registro_lastname")%></h2>
                <h2 class="resultado_h2">Correo: <%= request.getAttribute("registro_mail")%></h2>
                <h2 class="resultado_h2">Puesto: <%= request.getAttribute("registro_job")%></h2>
                <h2 class="resultado_h2">Keywords: <%= request.getAttribute("registro_keywords")%></h2>
                <h2 class="resultado_h2">Institucion: <%= request.getAttribute("registro_institucion")%></h2>
            </div>
        <%}%>
        <% if (request.getAttribute("type").equals("error")){ %>
            <div id="resultado_id_div_error">
                <h1 class="resultado_h1_error"><%= request.getAttribute("error") %></h1>            
            </div>
        <%}%>
        <div>
            <button type="button" onclick="callInicioJSP()" id="id_resultado_button_back" class="resultado_button_back">Volver</button>
        </div> 
    </body>
</html>
