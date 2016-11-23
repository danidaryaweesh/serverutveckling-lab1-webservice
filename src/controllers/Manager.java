package controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by dani on 2016-11-23.
 */
public class Manager {

    private static EntityManagerFactory emf=null;
    private static EntityManager em=null;


    public static EntityManager getInstance() {

        if(emf==null) {
            emf = Persistence.createEntityManagerFactory("TestPU");
            if(em == null){
                em = emf.createEntityManager();
            }
        }
        return em;
    }

}
