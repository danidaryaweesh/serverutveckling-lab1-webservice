package service.serviceImpl;

import model.User;
import service.UserService;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alican on 2016-11-21.
 */


public class UserServiceImpl implements UserService {

    private EntityManagerFactory emf;
    private EntityManager em;

    public UserServiceImpl(){
        emf = Persistence.createEntityManagerFactory("TestPU");
        em = emf.createEntityManager();
    }//UserServiceImpl

    @Override
    public User login(User user){
        List<User> userList = checkLogin(user);

        if(!userList.isEmpty() && userList.size() < 2){
            User returnedUser = userList.get(0);
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
        User realUser = checkUserExsistence(user);

        if(realUser != null){
            System.out.println(realUser.getUsername());
            return realUser;
        }//if
        else{
            //user does not exist

            EntityTransaction tx = null;
            try {
                tx = em.getTransaction();
                tx.begin();
                em.persist(user);
                tx.commit();
                System.out.println("Registerd new user!");
            }catch(RuntimeException e){
                if ( tx != null && tx.isActive() )
                    tx.rollback();
                throw e;
            }

            return checkUserExsistence(user);
        }//else
    }//register

    @Override
    public User findUser(int id) {
        System.out.println("IN HERE!!!");
        User user = em.find(User.class, id);
        System.out.println("FAILING TO REACH THIS!");
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        List<User> users = getUser(username);
        System.out.println("Length of list in find: "+users.size());
        if(users.size() > 0){
            return users.get(0);
        }else{
            System.out.println("Gonna return null in find!");
            return null;
        }
    }

    private User checkUserExsistence(User user){
        User realUser = em.find(User.class, user.getId());
        return realUser;
    }

    private List<User> getUser(String username){
        List<User> userList;
        Query q = em.createQuery("SELECT user FROM User user WHERE user.username=?1");
        q.setParameter(1, username);
        userList = q.getResultList();
        System.out.println("****Length of the list is: "+userList.size());
        return userList;
    }

    private List<User> checkLogin(User user){
        List<User> userList;
        Query q = em.createQuery("SELECT user FROM User user WHERE user.username=?1 AND user.password=?2");
        q.setParameter(1, user.getUsername());
        q.setParameter(2, user.getPassword());
        userList = q.getResultList();
        System.out.println("****Length of the list is: "+userList.size());
        return userList;
    }
}//class