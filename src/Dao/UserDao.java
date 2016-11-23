package Dao;

import java.util.List;

/**
 * Created by Alican on 2016-11-21.
 */
public class UserDao {
    private int id;
    private String username;
    private List<LogDao> log;
    private List<MessageDao> senderMessage;
    private List<MessageDao> recieverMessage;
    private int age;
    private String address;
    private String workTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<LogDao> getLog() {
        return log;
    }

    public void setLog(List<LogDao> log) {
        this.log = log;
    }

    public List<MessageDao> getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(List<MessageDao> senderMessage) {
        this.senderMessage = senderMessage;
    }

    public List<MessageDao> getRecieverMessage() {
        return recieverMessage;
    }

    public void setRecieverMessage(List<MessageDao> recieverMessage) {
        this.recieverMessage = recieverMessage;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }
}
