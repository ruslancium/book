package name.ruslan.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool<T extends ProxyConnection> {	
	
	private ArrayBlockingQueue<T> connectionQueue;
    //private static Logger logger = LogManager.getLogger(ConnectionManager.class);
	    
	ConnectionPool(Class<T> genericClass, int poolSize, Connection con) {
		 
		try {
			Driver driver = DriverManager.getDriver(con.getMetaData().getURL());
			DriverManager.registerDriver(driver);
			
			connectionQueue = new ArrayBlockingQueue<T>(poolSize);

			for (int i = 0; i < poolSize; i++) {
				Constructor<T> constructor;
				T connection = null;
				try {
					constructor = genericClass.getDeclaredConstructor(Connection.class);
					constructor.setAccessible(true);									
					connection = constructor.newInstance(con);
					connectionQueue.offer(connection);
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}	

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}    	
	}

	public T getConnection() throws InterruptedException {
		//logger.info("Connection taken...");
		return connectionQueue.take();
	}

	public void returnConnection(T connection) {
		connectionQueue.offer(connection);
		//logger.info("Connection returned");
	}

	public void releaseConnections() throws InterruptedException, SQLException {
		T connection = null; 
		while (true) {
			connection = connectionQueue.take();
			if (connection == null) {
				break;
			} else {
				connection.closeConnection();
			}
		}
	}
}