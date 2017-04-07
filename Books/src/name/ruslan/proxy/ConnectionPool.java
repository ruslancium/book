package name.ruslan.proxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool<T extends Connection> {
	private ArrayBlockingQueue<T> connectionQueue;
	private int poolSize;
	
	public ConnectionPool(final int POOL_SIZE, String lookupString) {
		connectionQueue = new ArrayBlockingQueue<T>(POOL_SIZE);
		for (int i = 0; i < POOL_SIZE; i++) {
			//Context ctx = new InitialContext();
			//DataSource  dataSource = (DataSource) ctx.lookup(lookupString);
			//T connection = (T) dataSource.getConnection();
			//все написать в драйверменеджере
			connectionQueue.offer(connection);			
		}
		
		this.poolSize = POOL_SIZE;
	}
   
	public T getConnection() throws InterruptedException {
		T connection = null;
		connection = connectionQueue.take();
		return connection;		
	}
	
	public void returnConnection(T connection) {
		connectionQueue.offer(connection); 
             // возможны утечки соединений
	}
	
	public void releaseConnections() throws InterruptedException, SQLException {
		for (int i = poolSize; i == 0; i--) {
			Connection connection = connectionQueue.take();
			connection.close();
		}
	}
	//more methods 
}

