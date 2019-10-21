
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import HibernatePojos.Instituciones;
import HibernatePojos.Investigadores;
import HibernatePojos.Publicaciones;
import Utilities.HibernateUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Carlos Araújo González
 */
@WebServlet(name = "signinServlet", urlPatterns = {"/SigninServlet"})
public class SigninServlet extends HttpServlet {

    private enum puesto {
        investigador, profesor, doctor
    };

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtenemos parametros de registro.jsp
        String name = request.getParameter("registro_name");
        String surname = request.getParameter("registro_surname");
        String lastname = request.getParameter("registro_lastname");
        String mail = request.getParameter("registro_mail");
        String job = request.getParameter("registro_job");
        String institucion = request.getParameter("registro_institucion");
        String keywords = request.getParameter("registro_keywords");
        String psw = request.getParameter("registro_psw");

        // Validamos el puesto
        validar_puesto(job, request, response);

        // Validamos la institución
        validar_institucion(institucion, request, response);

        // Obtenemos el objeto institución
        Instituciones in = findInstitucionByName(institucion);

        // Creamos objeto investigador
        Investigadores i = new Investigadores(
                in,
                false, // borrado lógico
                name,
                surname,
                lastname,
                mail,
                job,
                psw,
                null, // lista publis
                null // lista publis
        );

        // Indicamos atributos del investigador en la request
        request.setAttribute("type", "researcher");
        request.setAttribute("registro_name", i.getNombre());
        request.setAttribute("registro_surname", i.getApellido1());
        request.setAttribute("registro_lastname", i.getApellido2());
        request.setAttribute("registro_mail", i.getCorreo());
        request.setAttribute("registro_job", i.getPuesto());
        request.setAttribute("registro_institucion", in.getNombre());
        request.setAttribute("registro_keywords", keywords); // no en bbdd

        // Validamos si ya existe el investigador
        boolean valido = validar_investigador(i.getCorreo(), request, response);
        if (!valido) {
            return;
        }

        // Añadimos el investigador a la bbdd
        HibernateUtil.openSessionAndBindToThread();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session.beginTransaction();
            session.save(i);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            HibernateUtil.closeSessionAndUnbindFromThread();
        }
        request.setAttribute("text", "Investigador dado de alta");
        request.getRequestDispatcher("resultado.jsp").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Instituciones findInstitucionByName(String name) {
        List<Instituciones> is;
        try {
            HibernateUtil.openSessionAndBindToThread();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Query query = session.createQuery(
                    "SELECT i FROM Instituciones i"
            );
            is = query.list();
        } finally {
            HibernateUtil.closeSessionAndUnbindFromThread();
        }

        for (int i = 0; i < is.size(); i++) {
            if (is.get(i).getNombre().equals(name)) {
                return is.get(i);
            }
        }
        return null;
    }

    private void validar_institucion(String institucion, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (institucion.equals("")) {
            return;
        }
        List<Instituciones> is;
        try {
            HibernateUtil.openSessionAndBindToThread();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Query query = session.createQuery(
                    "SELECT i FROM Instituciones i"
            );
            is = query.list();
        } finally {
            HibernateUtil.closeSessionAndUnbindFromThread();
        }

        boolean existe = false;
        for (int i = 0; i < is.size(); i++) {
            if (is.get(i).getNombre().equals(institucion)) {
                existe = true;
            }
        }
        if (!existe) {
            // Redireccionamos a error.jsp
            request.setAttribute("error", "La institución indicada no existe");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private boolean validar_investigador(String correo, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Investigadores> is;
        try {
            HibernateUtil.openSessionAndBindToThread();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Query query = session.createQuery(
                    "SELECT i FROM Investigadores i"
            );
            is = query.list();
        } finally {
            HibernateUtil.closeSessionAndUnbindFromThread();
        }
        for (int i = 0; i < is.size(); i++) {
            if (is.get(i).getCorreo().equals(correo)) {
                // Redireccionamos a error.jsp
                request.setAttribute("text", "Investigador ya existe");
                request.getRequestDispatcher("resultado.jsp").forward(request, response);
                return false;
            }
        }
        return true;
    }

    private void validar_puesto(String job, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (job.equals("")) {
            return;
        }
        boolean existe = false;
        for (puesto value : puesto.values()) {
            if (job.equals(value.toString())) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            // Redireccionamos a error.jsp
            request.setAttribute("error", "El puesto indicado no existe");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
