package Dao;

import java.util.Calendar;

/**
 * Created by Alican on 2016-11-21.
 */
public class MessageDao {
    private int id;
    private UserDao sender;
    private UserDao reciever;
    private String content;
    private Calendar date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDao getSender() {
        return sender;
    }

    public void setSender(UserDao sender) {
        this.sender = sender;
    }

    public UserDao getReciever() {
        return reciever;
    }

    public void setReciever(UserDao reciever) {
        this.reciever = reciever;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
