package service.serviceImpl;

import model.User;
import service.UserService;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alican on 2016-11-21.
 */
public class UserServiceImpl implements UserService {
    private EntityManager em;

    public UserServiceImpl(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
    }//UserServiceImpl

    @Override
    public User login(User user){
        User returnedUser = new User();
        List<User> userList = getUser(user);

        if(!userList.isEmpty() && userList.size() < 2){
            returnedUser = userList.get(0);
            System.out.println("The username is: "+returnedUser.getUsername());
            return returnedUser;
        }//if
        else{
            //user does not exist, login failed
            return null;
        }//else
    }//login

    @Override
    public User register(User user) {
        List<User> userList = getUser(user);

        if(!userList.isEmpty() && userList.size() < 2){
            System.out.println(userList.get(0).getUsername());
            return userList.get(0);
        }//if
        else{
            //user does not exist, login failed
            em.getTransaction().begin();
            em.setFlushMode(FlushModeType.COMMIT);
            em.persist(user);
            em.getTransaction().commit();
            System.out.println("Registred new user!");
            return null;
        }//else
    }//register

    private List<User> getUser(User user){
        List<User> userList;
        Query q = em.createQuery("SELECT user FROM User user WHERE user.username=?1 AND user.password=?2");
        q.setParameter(1, user.getUsername());
        q.setParameter(2, user.getPassword());
        userList = q.getResultList();
        System.out.println("****Length of the list is: "+userList.size());
        return userList;
    }
}//class

