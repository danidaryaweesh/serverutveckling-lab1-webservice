package service.serviceImpl;

import model.Log;
import model.User;
import service.LogService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Alican on 2016-11-21.
 */
public class LogServiceImpl implements LogService {
    private EntityManager em;

    public LogServiceImpl(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
    }

    @Override
    public void addLog(Log log){
        em.getTransaction().begin();
        em.persist(log);
        em.getTransaction().commit();
    }//addLog

    @Override
    public void getLogs(User user) {
        em.getTransaction().begin();
        em.find(user.getLog().getClass(), user.getUsername());
        //return result
    }//getLogs

}//class