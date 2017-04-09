package name.ruslan.proxy;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
    private static Connection con;
    private static ConnectionManager instance;
    private static DataSource dataSource;
    private static ConnectionPool<ProxyConnection> connectionPool;
    private static Context ctx;
    private static final int POOL_SIZE = 20;
    
    private ConnectionManager() {
        if (instance == null) {
            try {            	
                ctx = new InitialContext();
                instance.dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/books");                
                con = instance.dataSource.getConnection();
                
                int poolSize = POOL_SIZE;
                
                connectionPool = new ConnectionPool<ProxyConnection>(ProxyConnection.class, poolSize, con);
            } catch (NamingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }    	
    }

    public static synchronized ConnectionManager getInstance() {
    	if (instance == null)
    		instance = new ConnectionManager();
    	return instance;    	
    }
    
    public ProxyConnection getConnection() {
    	try {
			return connectionPool.getConnection();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
	public void returnConnection(ProxyConnection connection) {
		connectionPool.returnConnection(connection);
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