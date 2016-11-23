package service;

import model.Log;
import model.User;

/**
 * Created by Alican on 2016-11-21.
 */
public interface LogService {
    public void addLog(Log log);
    public void getLogs(User user);
}
