package service.serviceImpl;

import model.Log;
import model.User;
import service.LogService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by Alican on 2016-11-21.
 */
public class LogServiceImpl implements LogService {
    private EntityManagerFactory emf;
    private EntityManager em;

    public LogServiceImpl(){
        emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
    }

    @Override
    public void addLog(Log log){
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(log);
            tx.commit();
            System.out.println("Registerd new Log!");
        }catch(RuntimeException e){
            if ( tx != null && tx.isActive() )
                tx.rollback();
            throw e;
        }finally {
            System.out.println("added log: "+log.getTitle());
        }
    }//addLog

    @Override
    public void getLogs(User user) {
        em.getTransaction().begin();
        em.find(user.getLog().getClass(), user.getUsername());
        //return result
    }//getLogs

}//class