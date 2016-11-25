package controllers;

import Dao.LogDao;
import Dao.MessageDao;
import Dao.UserAuthentication;
import Dao.UserDao;
import com.google.gson.Gson;
import model.Log;
import model.Message;
import model.User;
import service.serviceImpl.UserServiceImpl;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by dani on 2016-11-23.
 */

@Path("/user")
public class UserController {

    private UserServiceImpl userService;

    @Path("/login")
    @GET
    @Produces("text/plain")
    public String login(@QueryParam("userDaoJson") String userDaoJson){
        if(userService == null){
            userService = new UserServiceImpl();
            System.out.println("Created new userservice in login");
        }
        Gson gson = new Gson();
        System.out.println("Got the right one!");
        System.out.println("userDaoJson is:*** "+userDaoJson);
        UserAuthentication userDao = gson.fromJson(userDaoJson, UserAuthentication.class);
        System.out.println("UserDao is: "+userDao);
        User user = userService.findUserByUsername(userDao.getUsername());
        if(user != null){

            User checkLoginUser = userService.login(user);
            if(checkLoginUser!= null){
                UserDao userDaoToReturn = convertToUserDao(user);
                String userDaoJsonToReturn = gson.toJson(userDaoToReturn);
                return userDaoJsonToReturn;
            }else {
                return "Empty";
            }
        }else {
            return "Empty";
        }
    }

    @Path(value = "{userid}")
    @GET
    @Produces("text/plain")
    public String showUserInfo(@PathParam(value = "userid") int userid){
       if(userService == null) {
           userService = new UserServiceImpl();
           System.out.println("Created new userservice in showUserInfo");
       }

        User user = userService.findUser(userid);
        UserDao userDao= null;
        if(user != null){
            // user exist
            System.out.println("Users log list length: "+user.getLog().size());
            Gson gson = new Gson();
            userDao = convertToUserDao(user);
            String userDaoJson = gson.toJson(userDao);

            return userDaoJson;

        }else{
            return "Empty";
        }
    }

    @Path(value = "/username/{username}")
    @GET
    @Produces("text/plain")
    public String getUserWithUsername(@PathParam(value = "username") String username){
        if(userService == null) {
            userService = new UserServiceImpl();
            System.out.println("Created new userservice in getUserWithUsername");
        }

        User user = userService.findUserByUsername(username);
        UserDao userDao=null;
        if(user != null){
            Gson gson = new Gson();
            userDao = convertToUserDao(user);
            String userDaoJson = gson.toJson(userDao);

            return userDaoJson;
        }else{
            return "Empty";
        }
    }

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public String addUser(String userDaoJson){
        if(userService == null){
            userService = new UserServiceImpl();
            System.out.println("Created new userservice in adduser");
        }
        System.out.println("UserdaoJson in adduser: "+userDaoJson);
        Gson gson = new Gson();
        UserAuthentication userAuthentication = gson.fromJson(userDaoJson, UserAuthentication.class);

        System.out.println("After GSON converstion: "+userAuthentication.getUsername());
        User user = userService.findUserByUsername(userAuthentication.getUsername());

        if(user == null){
            User userTosave = new User();
            userTosave.setUsername(userAuthentication.getUsername());
            userTosave.setPassword(userAuthentication.getPassword());
            userTosave.setAddress(userAuthentication.getAddress());
            userTosave.setAge(userAuthentication.getAge());
            userTosave.setWorkTitle(userAuthentication.getWorkTitle());

            user = userService.register(userTosave);

            System.out.println("Registerd in controller user: "+user.getUsername());

            return userDaoJson;
        }else{
            System.out.println("User exist!");
            return "Empty";
        }
    }

    private UserDao convertToUserDao(User user){
        UserDao userDao = new UserDao();
        LogDao logDao = new LogDao();


        userDao.setId(user.getId());
        userDao.setUsername(user.getUsername());
        userDao.setAddress(user.getAddress());
        userDao.setAge(user.getAge());
        userDao.setLog(getLogDaos(user.getLog()));
        // System.out.println("Log in userDao: "+userDao.getLog().get(0).getTitle());
        userDao.setRecieverMessage(getMessageDaos(user.getRecieverMessage()));
        userDao.setSenderMessage(getMessageDaos(user.getSenderMessage()));

        return userDao;
    }

    private List<MessageDao> getMessageDaos(List<Message> messages){
        List<MessageDao> listToReturn = new ArrayList<MessageDao>();

        for(int i=0;i<messages.size();i++){
            MessageDao dao = new MessageDao();
            dao.setId(messages.get(i).getId());
           // dao.setDate(messages.get(i).getDate());
        }

        return listToReturn;
    }

    private List<LogDao> getLogDaos(List<Log> logs){
        List<LogDao> daoLogs = new ArrayList<LogDao>();

        for(int i=0;i<logs.size();i++){
            LogDao dao = new LogDao();

            dao.setId(logs.get(i).getId());
            dao.setContent(logs.get(i).getContent());
           // dao.setDate(logs.get(i).getDate());
            dao.setTitle(logs.get(i).getTitle());
            daoLogs.add(dao);
        }
        return daoLogs;
    }
}