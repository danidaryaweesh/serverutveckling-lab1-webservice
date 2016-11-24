import controllers.HelloWorld;
import controllers.LogController;
import controllers.UserController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dani on 2016-11-21.
 */

@ApplicationPath("/")
public class MyApplication extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(HelloWorld.class );
        h.add(UserController.class);
        h.add(LogController.class);
        return h;
    }
}
