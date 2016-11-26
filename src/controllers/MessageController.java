package controllers;

import Dao.MessageDao;
import com.google.gson.Gson;
import model.Message;
import model.User;
import service.MessageService;
import service.UserService;
import service.serviceImpl.MessageServiceImpl;
import service.serviceImpl.UserServiceImpl;

import javax.ws.rs.*;

/**
 * Created by dani on 2016-11-25.
 */

@Path("/message")
public class MessageController {

    private UserService userService;
    private MessageService messageService;

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public String getMessage(String messageDao){
        System.out.println("before ifs..");
        if(userService == null){
            userService = new UserServiceImpl();
        }
        if(messageService == null){
            messageService = new MessageServiceImpl();
        }
        System.out.println("Adding a message after ifs..");
        Gson gson = new Gson();
        MessageDao messageToSave = gson.fromJson(messageDao, MessageDao.class);

        System.out.println("content of Message recieved: "+messageToSave.getContent());

        User userSender = userService.findUser(messageToSave.getSender().getId());
        User userReciever = userService.findUserByUsername(messageToSave.getReciever());

        System.out.println("Sender and reciever: "+userSender.getUsername() + " , "+userReciever.getUsername());

        if(userSender != null && userReciever != null){
            System.out.println("In if not null for both sender and receiever!");
            Message realMessage = new Message();
            realMessage.setDate(messageToSave.getDate() !=null ? messageToSave.getDate() : null);
            realMessage.setContent(messageToSave.getContent());
            realMessage.setReciever(userReciever);
            realMessage.setSender(userSender);
            messageService.addMessage(realMessage);

            return messageDao;
        }else{
            return "Empty";
        }
    }
}
