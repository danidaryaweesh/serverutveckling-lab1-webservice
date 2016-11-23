package controllers;

import model.Test;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by dani on 2016-11-23.
 */

@Path("/user")
public class UserController {

    @GET
    @Produces("text/plain")
    public String getUser(){
        User user = new User();
        user.setUsername("Alican");
        user.setPassword("Bircan");
        user.setAge(21);
        user.setAddress("Haninge");
        user.setWorkTitle("Geeka");

        // måste fixas på något sätt?
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.setFlushMode(FlushModeType.COMMIT);
        em.persist(user);
        em.getTransaction().commit();
        System.out.println("Registred new user!");
        em.close();
        return "BOB";
    }

    @Path("/id")
    @GET
    @Produces("application/json")
    public Test getID(){
        Test test = new Test();
        test.setI("Hello");
        test.setL("World");

        return test;
    }
}
