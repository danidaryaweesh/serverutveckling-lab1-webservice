package service.serviceImpl;

import model.Message;
import service.MessageService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by Alican on 2016-11-21.
 */
public class MessageServiceImpl implements MessageService {

    private EntityManagerFactory emf;
    private EntityManager em;

    public MessageServiceImpl(){
        emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
    }

    @Override
    public void addMessage(Message message) {
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(message);
            tx.commit();
            System.out.println("Registerd a new Message!");
        }catch(RuntimeException e){
            if ( tx != null && tx.isActive() )
                tx.rollback();
            throw e;
        }
    }
}
