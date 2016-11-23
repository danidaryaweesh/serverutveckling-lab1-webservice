package Dao;

import java.util.Calendar;

/**
 * Created by Alican on 2016-11-21.
 */
public class LogDao {
    private int id;
    private UserDao owner;
    private String title;
    private String content;
    private Calendar date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDao getOwner() {
        return owner;
    }

    public void setOwner(UserDao owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
