/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ThreadLocalSessionContext;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Carlos Araújo González
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static synchronized void buildSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Create the SessionFactory from standard (hibernate.cfg.xml)
                // config file.
                //logger.info("Trying to create a test connection with the database.");
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(ssrb.build());
                //logger.info("Test connection with the database created successfuly.");
            } catch (HibernateException ex) {
                // Log the exception.
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
    }

    public static void beginTransaction() {
        sessionFactory.openSession().beginTransaction();
    }

    public static void commit() {
        sessionFactory.openSession().getTransaction().commit();
    }

    public static void openSessionAndBindToThread() {
        Session session = sessionFactory.openSession();
        ThreadLocalSessionContext.bind(session);
    }

    public static void rollback() {
        sessionFactory.openSession().getTransaction().rollback();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void closeSessionAndUnbindFromThread() {
        Session session = ThreadLocalSessionContext.unbind(sessionFactory);
        if (session != null) {
            session.close();
        }
    }

    public static void closeSessionFactory() {
        if ((sessionFactory != null) && (sessionFactory.isClosed() == false)) {
            sessionFactory.close();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////// EJEMPLO GUARDAR HIBERNATE /////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    /*

    Profesor profesor=new Profesor(101, "Juan", "Perez", "García");  //Creamos el objeto

    Session session = sessionFactory.openSession();
    session.beginTransaction();

    session.save(profesor); //<|--- Aqui guardamos el objeto en la base de datos.

    session.getTransaction().commit();
    session.close();


    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////// EJEMPLO LEER HIBERNATE ////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    Profesor profesor=(Profesor)session.get(profesor.class,101);


    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////// EJEMPLO ACTUALIZAR HIBERNATE //////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    session.beginTransaction();
    session.update(profesor);
    session.getTransaction().commit();


    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////// EJEMPLO BORRAR HIBERNATE //////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    session.beginTransaction();
    session.delete(profesor);
    session.getTransaction().commit();


     ////////////////////////////////////////////////////////////////////////////////////
    /////////// EJEMPLO GUARDAR O ACTUALIZAR HIBERNATE //////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    // Si no quieres estar pendiente de si hay que insertar o actualizar
    Profesor profesor=new Profesor(101, "Juan", "Perez", "García");
    session.beginTransaction();
    session.saveOrUpdate(profesor);
    session.getTransaction().commit();

     */
}
