<%--
    Document   : publicaciones
    Created on : 26-jun-2018, 13:23:03
    Author     : Carlos Araújo González
--%>

<%@page import="org.hibernate.Session"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="HibernatePojos.Investigadores"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.Query"%>
<%@page import="HibernatePojos.Publicaciones"%>
<%@page import="Utilities.HibernateUtil"%>
<%@page import="Controlador.DBController"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Publicaciones</title>
        <link rel="stylesheet" href="styles.css">
        <script src="myscripts.js"></script>
    </head>
    <body onload="modal()">
        <%

            HttpSession misession = request.getSession(true);
            int id = (int) misession.getAttribute("id_investigador");
            String mail = (String) misession.getAttribute("mail");
            // Obtenemos lista de publicaciones
            List<Publicaciones> ps;
            try {
                HibernateUtil.openSessionAndBindToThread();
                Session s = HibernateUtil.getSessionFactory().getCurrentSession();
                Query query = s.createQuery(
                        "SELECT p FROM Publicaciones p"
                );
                ps = query.list();
                for (int i = 0; i < ps.size(); i++) {
                    boolean existe = false;
                    Set<Investigadores> is = ps.get(i).getInvestigadoreses();
                    for (Iterator<Investigadores> it = is.iterator(); it.hasNext();) {
                        if (it.next().getCorreo().equals(mail)) {
                            System.out.println("Existe este correo :" + request.getAttribute("mail"));
                            existe = true;
                        }
                    }
                    if (!existe) {
                        ps.remove(i);
                    }
                }
        %>
        <h1 class="publishes_h1">Lista de publicaciones del usuario</h1>
        <div>
            <table width="100%" id="publishes_table" class="publishesbyuser_table">
                <tbody>
                <th class="publishesbyuser_th">Título</th>
                <th class="publishesbyuser_th">Año</th>
                <th class="publishesbyuser_th">Primera página</th>
                <th class="publishesbyuser_th">Última página</th>
                <th class="publishesbyuser_th">Nombre del autor de contacto</th>
                <th class="publishesbyuser_th">Apellido del autor de contacto</th>
                    <% for (int k = 0; k < ps.size(); k++) {%>
                <tr bgcolor="e7e7e7">
                    <td class="publishesbyuser_td"><%=ps.get(k).getTitulo()%></td>
                    <td class="publishesbyuser_td"><%=ps.get(k).getYear()%></td>
                    <td class="publishesbyuser_td"><%=ps.get(k).getFirstPage()%></td>
                    <td class="publishesbyuser_td"><%=ps.get(k).getLastPage()%></td>
                    <td class="publishesbyuser_td"><%=ps.get(k).getInvestigadores().getNombre()%></td>
                    <td class="publishesbyuser_td"><%=ps.get(k).getInvestigadores().getApellido1()%></td>
                </tr>
                <%}
                    } finally {
                        HibernateUtil.closeSessionAndUnbindFromThread();
                    }%>
                </tbody>
            </table>
        </div>
        <div>
            <button type="button" id="publishes_button_add" class="publishes_button">Añadir</button>
            <button type="button" id="publishes_button_update" class="publishes_button">Modificar</button>
            <button type="button" id="publishes_button_delete" class="publishes_button">Eliminar</button>
        </div>
        <div id="publishes_modal" class="modal">
            <div class="modal-content">
                <form action="PublishServlet" method="post">
                    <span class="close">&times;</span>
                    <h2 id="publishes_h2"></h2>
                    <table width="100%" id="popup_table" class="publishesbyuser_table">
                        <tbody>
                        <th class="publishesbyuser_th">Título</th>
                        <th class="publishesbyuser_th">Año</th>
                        <th class="publishesbyuser_th">Primera página</th>
                        <th class="publishesbyuser_th">Última página</th>
                        <th class="publishesbyuser_th">Nombre del autor de contacto</th>
                        <th class="publishesbyuser_th">Apellido del autor de contacto</th>
                        <tr bgcolor="e7e7e7">
                            <td class="publishesbyuser_td">
                                <input type="text" placeholder="Título" name="title"></input>
                            </td>
                            <td class="publishesbyuser_td">
                                <input type="number" maxlength="4" placeholder="Año" name="year"></input>
                            </td>
                            <td class="publishesbyuser_td">
                                <input type="number" placeholder="Primera página" name="first_page"></input>
                            </td>
                            <td class="publishesbyuser_td">
                                <input type="number" placeholder="Última página" name="last_page"></input>
                            </td>
                            <td class="publishesbyuser_td">
                                <input type="text" placeholder="Nombre" name="nombre_autor_contacto"></input>
                            </td>
                            <td class="publishesbyuser_td">
                                <input type="text" placeholder="Apellido" name="apellido_autor_contacto"></input>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <input type="hidden" id="id_publicaciones_input" name="publicaciones_input">
                    <button type="submit" value="submit" id="publishes_button_ok" class="publishes_button_add"></button>
                </form>
            </div>
        </div>
    </body>
</html>
