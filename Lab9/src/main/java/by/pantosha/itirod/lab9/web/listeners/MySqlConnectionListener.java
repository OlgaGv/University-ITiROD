package by.pantosha.itirod.lab9.web.listeners;


import com.mysql.jdbc.Driver;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class MySqlConnectionListener implements ServletContextListener {
    private Driver _mySqlDriver;

    public MySqlConnectionListener() {
        super();
        try {
            _mySqlDriver = new com.mysql.jdbc.Driver();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            DriverManager.registerDriver(_mySqlDriver);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            DriverManager.deregisterDriver(_mySqlDriver);
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
