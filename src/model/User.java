package model; /**
 * Created by dani on 2016-11-18.
 */
import javax.persistence.*;

import java.util.List;

/**
 * Created by dani on 2016-11-09.
 */
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, length = 45)
    private String username;

    @Column(nullable = false, length = 80)
    private String password;

    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "owner")
    private List<Log> log;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Message> senderMessage;

    @OneToMany(mappedBy = "reciever")
    private List<Message> recieverMessage;

    private int age;
    private String address;
    private String workTitle;

    public List<Log> getLog() {
        return log;
    }

    public void setLog(List<Log> log) {
        this.log = log;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getSenderMessage() {
        return senderMessage;
    }

    public void setSenderMessage(List<Message> senderMessage) {
        this.senderMessage = senderMessage;
    }

    public List<Message> getRecieverMessage() {
        return recieverMessage;
    }

    public void setRecieverMessage(List<Message> recieverMessage) {
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

