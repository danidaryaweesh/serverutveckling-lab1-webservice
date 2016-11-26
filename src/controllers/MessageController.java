package controllers;

import Dao.MessageDao;
import Dao.UserDao;
import com.google.gson.Gson;
import model.Message;
import model.User;
import service.MessageService;
import service.UserService;
import service.serviceImpl.MessageServiceImpl;
import service.serviceImpl.UserServiceImpl;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dani on 2016-11-25.
 */

@Path("/message")
public class MessageController {

    private UserService userService;
    private MessageService messageService;

    @Path("/{username}")
    @GET
    @Produces
    public String getMessagesWithUser(@PathParam(value = "username") String username){
        if(userService == null){
            userService = new UserServiceImpl();
        }
        if(messageService == null){
            messageService = new MessageServiceImpl();
        }
        System.out.println("Getting message..");
        User user = userService.findUserByUsername(username);

        if(user != null){
            List<MessageDao> messagesToReturn = getMessageDaos(user.getRecieverMessage());
            Gson gson = new Gson();
            String response = gson.toJson(messagesToReturn);
            return response;
        }else{
            return "Empty";
        }
    }

    @POST
    @Consumes("text/plain")
    @Produces("text/plain")
    public String addMessage(String messageDao){
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

    private List<MessageDao> getMessageDaos(List<Message> messages){
        List<MessageDao> listToReturn = new ArrayList<MessageDao>();

        for(int i=0;i<messages.size();i++){
            MessageDao dao = new MessageDao();
            dao.setId(messages.get(i).getId());
            dao.setDate(messages.get(i).getDate() !=null ? messages.get(i).getDate() : null);
            dao.setContent(messages.get(i).getContent());
            dao.setSender(convertMessageUserToDao(messages.get(i).getSender()));
            listToReturn.add(dao);
        }
        return listToReturn;
    }

    private UserDao convertMessageUserToDao(User user){
        UserDao userDaoToReturn = new UserDao();
        userDaoToReturn.setId(user.getId());
        userDaoToReturn.setUsername(user.getUsername());
        userDaoToReturn.setAddress(user.getAddress());
        userDaoToReturn.setWorkTitle(user.getWorkTitle());
        userDaoToReturn.setAge(user.getAge());
        return userDaoToReturn;
    }
}
