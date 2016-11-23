package service.serviceImpl;

import model.Message;
import model.User;
import service.MessageService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;

/**
 * Created by Alican on 2016-11-21.
 */
public class MessageServiceImpl implements MessageService {

    @Context
    private EntityManager em;

    public MessageServiceImpl(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void getMessagesFromUser(User user) {

    }
}
