package service;

import model.Message;
import model.User;

/**
 * Created by Alican on 2016-11-21.
 */
public interface MessageService{
    public void sendMessage(Message message);
    public void getMessagesFromUser(User user);
}
