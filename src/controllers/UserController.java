package controllers;

import Dao.LogDao;
import Dao.MessageDao;
import Dao.UserAuthentication;
import Dao.UserDao;
import com.google.gson.Gson;
import model.Log;
import model.Message;
import model.User;
import service.UserService;
import service.serviceImpl.UserServiceImpl;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by dani on 2016-11-23.
 */

// GSON parsing: Parsa objekt i nivå 2 och lägg till den med (ta bort den från orginalet genom toString --> antar att den utgår från toString!

// ex: A skickar till b . A skickar JsonCreator och B fångar som vanligt och när den vill skicka något så parsar den genom
    // json creator och skickar som vanligt i en vanlig string som består av båda och mottagaren fångar som vanligt!

@Path("/user")
public class UserController {

    private UserService userService;


    @Path("/login")
    @GET
    @Produces("text/plain")
    public String login(@QueryParam("userDaoJson") String userDaoJson){
        if(userService == null){
            userService = new UserServiceImpl();
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
       if(userService == null)
            userService = new UserServiceImpl();

        User user = userService.findUser(userid);
        UserDao userDao=null;
        if(user != null){
            // user exist
            System.out.println("Users log list length: "+user.getLog().size());
            Gson gson = new Gson();
            userDao = convertToUserDao(user);
            String userDaoJson = gson.toJson(userDao);

            return userDaoJson;

        }else{
            return "empty";
        }
    }

    @Path(value = "/username/{username}")
    @GET
    @Produces("text/plain")
    public String getUserWithUsername(@PathParam(value = "username") String username){
        if(userService == null)
            userService = new UserServiceImpl();
        User user = userService.findUserByUsername(username);
        UserDao userDao=null;
        if(user != null){
            Gson gson = new Gson();
            userDao = convertToUserDao(user);
            String userDaoJson = gson.toJson(userDao);

            return userDaoJson;
        }else{
            return "empty";
        }
    }

    @POST
    @Produces("text/plain")
    public String addUser(@QueryParam("userDaoJson") String userDaoJson){
        Gson gson = new Gson();
        UserDao userDao = gson.fromJson(userDaoJson, UserDao.class);
        User user = userService.findUserByUsername(userDao.getUsername());

        if(user == null){
            user = userService.register(user);
            UserDao userDaoToReturn = convertToUserDao(user);
            String userDaoJsonToReturn = gson.toJson(userDao);

            return userDaoJsonToReturn;
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

        for(int i=0;i<daoLogs.size();i++){
            LogDao dao = new LogDao();

            dao.setId(daoLogs.get(i).getId());
            dao.setContent(daoLogs.get(i).getContent());
           // dao.setDate(daoLogs.get(i).getDate());
            dao.setTitle(daoLogs.get(i).getTitle());
            daoLogs.add(dao);
        }
        return daoLogs;
    }
}
