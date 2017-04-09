package name.ruslan.manager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import name.ruslan.proxy.ConnectionPool;
import name.ruslan.proxy.ProxyConnection;

public class ConnectionManager {
    private static Connection con;
    private static ConnectionManager instance;
    private static DataSource dataSource;
    private static ConnectionPool<ProxyConnection> connectionPool;
    
    private ConnectionManager() {
        if (instance == null) {
            try {                
                Context ctx = new InitialContext();
                instance.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/books");
                con = dataSource.getConnection();
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }    	
    }

    public ConnectionManager getInstance() {
    	instance = new ConnectionManager();
    	return instance;    	
    }
    
    public Connection getConnection() {
    	try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public void registerDriver() {
    	
    }
    
    public void deregisterDrivers() {
    	
    	
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();            
                
            try {
            	DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }
    }

}
