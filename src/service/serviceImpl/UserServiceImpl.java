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
        User returnedUser = new User();
        List<User> userList = checkLogin(user);

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
        if(users.size() > 0){
            return users.get(0);
        }else{
            return null;
        }
    }

    /*private void test(){
     // EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
       // EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;

        try {
            if(checkUserExsistence(new User()).size() < 1) {
                tx = em.getTransaction();
                tx.begin();
                em.persist(new User());
                tx.commit();
            }else{
                System.out.println("User already exist!");
            }
        }catch(RuntimeException e){
            if ( tx != null && tx.isActive() )
                tx.rollback();
            throw e;
        }finally {
            System.out.println("Registred new user!");
            em.close();
        }
    }*/

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

