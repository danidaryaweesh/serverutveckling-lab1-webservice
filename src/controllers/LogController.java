package controllers;

import Dao.LogDao;
import Dao.UserDao;
import com.google.gson.Gson;
import model.Log;
import model.User;
import service.LogService;
import service.UserService;
import service.serviceImpl.LogServiceImpl;
import service.serviceImpl.UserServiceImpl;

import javax.ws.rs.*;

/**
 * Created by dani on 2016-11-24.
 */

@Path("/log")
public class LogController {

    private LogService logService;
    private UserService userService;

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public String addLog(String logDao){
        if(logService == null){
            logService = new LogServiceImpl();
        }
        if(userService == null){
            userService = new UserServiceImpl();
        }
        System.out.println("Adding a log! which is: "+logDao);
        Gson gson = new Gson();
        LogDao logToSave = gson.fromJson(logDao, LogDao.class);
        System.out.println("After Gson conversion: "+logToSave.getTitle());

        User user = userService.findUser(logToSave.getOwner().getId());
        System.out.println("After user find:" +user.getUsername());

        if(user!=null){
            System.out.println("found user: in if");
            Log realLog = new Log();

            realLog.setTitle(logToSave.getTitle());
            realLog.setContent(logToSave.getContent());
            realLog.setDate(logToSave.getDate() != null? logToSave.getDate() : null);
            realLog.setOwner(user);
            logService.addLog(realLog);

            return logDao;
        }else{
            return "Empty";
        }
    }

    @Path("{userid}")
    @GET
    @Produces("text/plain")
    public String showLogInfo(@PathParam(value = "userid") int userid) {
        if(logService == null){
            logService = new LogServiceImpl();
        }
        if (userService == null) {
            userService = new UserServiceImpl();
        }

        System.out.println("THROUGH ID: "+userid);

        User user = userService.findUser(userid);
        if (user != null) {
            // user exist
            Gson gson = new Gson();
            Log log = user.getLog().get(0);
            LogDao l = new LogDao();

            l.setId(log.getId());
            l.setTitle(log.getTitle());
            l.setContent(log.getContent());
            l.setDate(log.getDate());
            l.setOwner(convertUserToUserDao(log.getOwner()));

            String la = gson.toJson(l);
            return la;

        } else {
            return "Empty";
        }
    }

    private UserDao convertUserToUserDao(User user){
        UserDao userDao = new UserDao();
        userDao.setId(user.getId());
        userDao.setAge(user.getAge());
        userDao.setUsername(user.getUsername());
        userDao.setWorkTitle(user.getWorkTitle());
        userDao.setAddress(user.getAddress());

        return userDao;
    }
}
