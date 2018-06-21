package com.smartbe.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class T2TiEntityManagerFactory implements ServletContextListener {

    private static EntityManagerFactory factory;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        factory = Persistence.createEntityManagerFactory("smartbe");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
    
    public static EntityManager createEntityManager() throws Exception {
        if (factory == null || !factory.isOpen()) {
            throw new Exception("Erro ao criar o Entity Manager.");
        }
        return factory.createEntityManager();
    }    
}
