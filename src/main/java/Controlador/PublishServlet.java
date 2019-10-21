
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import HibernatePojos.Investigadores;
import HibernatePojos.Publicaciones;
import Utilities.HibernateUtil;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Carlos Araújo González
 */
@WebServlet(name = "publishServlet", urlPatterns = {"/PublishServlet"})
public class PublishServlet extends HttpServlet {

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

        response.setContentType("text/html;charset=UTF-8");

        switch (request.getParameter("publicaciones_input")) {
            case "add": // Añadimos publicación
                add(request, response);
                break;
            case "update": // Actualizamos publicación
                update(request, response);
                break;
            case "delete": // Borramos publicación
                delete(request, response);
                break;
        }
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

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String titulo = request.getParameter("title");
            int year = Integer.parseInt(request.getParameter("year"));
            int first_page = Integer.parseInt(request.getParameter("first_page"));
            int last_page = Integer.parseInt(request.getParameter("last_page"));
            String nombre_autor_contacto = request.getParameter("nombre_autor_contacto");
            String apellido_autor_contacto = request.getParameter("apellido_autor_contacto");

            // Obtenemos todas las publicaciones
            List<Publicaciones> ps = getAllPublishes();

            // Obtenemos el id de la publicación con el título indicado
            int id_publicacion = get_id_publicacion(ps, titulo);

            // Si ya existe la publicación llamamos a resultado.jsp con sus datos
            if (id_publicacion != 0) {
                request.setAttribute("type", "publish");
                request.setAttribute("title", titulo);
                request.setAttribute("year", year);
                request.setAttribute("first_page", first_page);
                request.setAttribute("last_page", last_page);
                request.setAttribute("nombre_autor_contacto", nombre_autor_contacto);
                request.setAttribute("apellido_autor_contacto", apellido_autor_contacto);
                request.getRequestDispatcher("resultado.jsp").forward(request, response);
                return;
            }

            // Buscamos al autor de contacto
            List<Investigadores> is = getAllResearchers();

            // Obtenemos el autor de contacto
            Investigadores autorContacto = getAutorContacto(is, nombre_autor_contacto, apellido_autor_contacto);

            // Si el autor de contacto no existe
            if (autorContacto.getIdInvestigador() == 0) {
                request.setAttribute("type", "error");
                request.setAttribute("error", "El autor de contacto no existe.");
                request.getRequestDispatcher("resultado.jsp").forward(request, response);
                return;
            }

            // Obtenemos el autor de la sesión
            HttpSession miSesion = request.getSession();
            int id_investigador = (int) miSesion.getAttribute("id_investigador");
            Investigadores autor = getAutorSesion(is, id_investigador);

            // Añadimos el autor a la lista de autores para crear la relación
            // publicación - autores
            Set<Investigadores> autores = new HashSet<>();
            autores.add(autor);
            Publicaciones p = new Publicaciones(
                    autorContacto,
                    titulo,
                    year,
                    first_page,
                    last_page,
                    autores
            );

            // Añadimos la publicación a bb.dd
            HibernateUtil.openSessionAndBindToThread();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            try {
                session.beginTransaction();
                session.save(p);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            } finally {
                HibernateUtil.closeSessionAndUnbindFromThread();
            }

            // Llamamos a resultado.jsp con la info de la nueva publicación
            request.setAttribute("type", "publish");
            request.setAttribute("title", p.getTitulo());
            request.setAttribute("year", p.getYear());
            request.setAttribute("first_page", p.getFirstPage());
            request.setAttribute("last_page", p.getLastPage());
            request.setAttribute("id_autor_contacto", autorContacto.getIdInvestigador());
            request.getRequestDispatcher("resultado.jsp").forward(request, response);
        } catch (NumberFormatException nfe) {
            // Si algo va mal volvemos a publicaciones.jsp
            request.setAttribute("mail", request.getSession().getAttribute("correo").toString());
            request.getRequestDispatcher("publicaciones.jsp").forward(request, response);
            Logger.getLogger(PublishServlet.class.getName()).log(Level.SEVERE, null, nfe);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession miSesion = request.getSession();
        int id_autor = (int) miSesion.getAttribute("id_investigador");
        String titulo = request.getParameter("title");
        try {
            // Obtenemos todas las publicaciones
            List<Publicaciones> ps = getAllPublishes();

            // Obtenemos el id de la publicación con el título indicado
            int id_publicacion = get_id_publicacion(ps, titulo);

            // Si no existe la publicación en la bbdd
            if (id_publicacion == 0) {
                System.out.println("No existe la publicación con ese título");
                return;
            }

            // Obtenemos la publicación a borrar
            Publicaciones p = getPublishById(ps, id_publicacion);
            if (p == null) {
                System.out.println("Ha habido un error buscando la publicación por id");
                return;
            }

            // Si no existe la publicación para ese autor
            if (!existeAutorPublicacion(p, id_autor)) {
                System.out.println("No existe la publicación para ese autor");
                return;
            }

            // Borramos la publicación de bb.dd
            HibernateUtil.openSessionAndBindToThread();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            try {
                session.beginTransaction();
                session.delete(p);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            } finally {
                HibernateUtil.closeSessionAndUnbindFromThread();
            }

            request.setAttribute("mail", miSesion.getAttribute("mail").toString());
            request.getRequestDispatcher("publicaciones.jsp").forward(request, response);
        } catch (NumberFormatException nfe) {
            request.setAttribute("mail", miSesion.getAttribute("mail").toString());
            request.getRequestDispatcher("publicaciones.jsp").forward(request, response);
            Logger.getLogger(PublishServlet.class.getName()).log(Level.SEVERE, null, nfe);
        }
    }

    private boolean existeAutorPublicacion(Publicaciones p, int id_autor) {
        try {
            HibernateUtil.openSessionAndBindToThread();
            Session s = HibernateUtil.getSessionFactory().getCurrentSession();
            Publicaciones pub = (Publicaciones) s.get(Publicaciones.class, p.getIdPublicacion());
            Set<Investigadores> is = pub.getInvestigadoreses();
            if (is == null) {
                return false;
            }
            for (Iterator<Investigadores> it = is.iterator(); it.hasNext();) {
                if (it.next().getIdInvestigador() == id_autor) {
                    return true;
                }
            }
            return false;
        } finally {
            HibernateUtil.closeSessionAndUnbindFromThread();
        }
    }

    private List<Publicaciones> getAllPublishes() {
        List<Publicaciones> ps;
        try {
            HibernateUtil.openSessionAndBindToThread();
            Session s = HibernateUtil.getSessionFactory().getCurrentSession();
            Query query = s.createQuery(
                    "SELECT p FROM Publicaciones p"
            );
            ps = query.list();
        } finally {
            HibernateUtil.closeSessionAndUnbindFromThread();
        }
        return ps;
    }

    private List<Investigadores> getAllResearchers() {
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
        return is;
    }

    private Investigadores getAutorContacto(List<Investigadores> is, String nombre, String apellido) {
        Investigadores autorContacto = null;
        for (int i = 0; i < is.size(); i++) {
            if (is.get(i).getNombre().equals(nombre)
                    & is.get(i).getApellido1().equals(apellido)) {
                autorContacto = is.get(i);
                break;
            }
        }
        return autorContacto;
    }

    private Investigadores getAutorSesion(List<Investigadores> is, int id) {
        Investigadores autor = null;
        for (int i = 0; i < is.size(); i++) {
            if (is.get(i).getIdInvestigador() == id) {
                autor = is.get(i);
                break;
            }
        }
        return autor;
    }

    private int get_id_publicacion(List<Publicaciones> ps, String titulo) {
        int id_publicacion = 0;
        for (int i = 0; i < ps.size(); i++) {
            if (ps.get(i).getTitulo().equals(titulo)) {
                id_publicacion = ps.get(i).getIdPublicacion();
            }
        }
        return id_publicacion;
    }

    private Publicaciones getPublishById(List<Publicaciones> ps, int id) {
        Publicaciones p = null;
        for (int i = 0; i < ps.size(); i++) {
            if (ps.get(i).getIdPublicacion() == id) {
                p = ps.get(i);
                break;
            }
        }
        return p;
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String titulo = request.getParameter("title");
            int year = Integer.parseInt(request.getParameter("year"));
            int first_page = Integer.parseInt(request.getParameter("first_page"));
            int last_page = Integer.parseInt(request.getParameter("last_page"));
            String nombre_autor_contacto = request.getParameter("nombre_autor_contacto");
            String apellido_autor_contacto = request.getParameter("apellido_autor_contacto");

            // Obtenemos la publicación con el título indicado
            List<Publicaciones> ps = getAllPublishes();
            int id_publicacion = get_id_publicacion(ps, titulo);
            Publicaciones p = getPublishById(ps, id_publicacion);

            // Actualizamos los datos en la publicación antes de guardar
            p.setYear(year);
            p.setFirstPage(first_page);
            p.setLastPage(last_page);
            List<Investigadores> is = getAllResearchers();
            Investigadores autorContacto = getAutorContacto(is, nombre_autor_contacto, apellido_autor_contacto);
            if (autorContacto != null) {
                p.setInvestigadores(autorContacto);
            }

            // Actualizamos la publicación en bb.dd
            HibernateUtil.openSessionAndBindToThread();
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            try {
                session.beginTransaction();
                session.update(p);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            } finally {
                HibernateUtil.closeSessionAndUnbindFromThread();
            }

            HttpSession miSesion = request.getSession();
            request.setAttribute("mail", miSesion.getAttribute("correo").toString());
            request.getRequestDispatcher("publicaciones.jsp").forward(request, response);
        } catch (NumberFormatException nfe) {
            response.sendRedirect("publicaciones.jsp");
            Logger.getLogger(PublishServlet.class.getName()).log(Level.SEVERE, null, nfe);
        }
    }
}
