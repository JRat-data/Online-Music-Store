package cs636.music.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import static cs636.music.dao.DBConstants.*;

/**
 * Database connection and initialization.
 * Implemented singleton on this class.
 * 
 */
@Repository
public class DbDAO {
	@Autowired
	private DataSource dataSource;  
	   
	/**
	 *  Use to connect to databases through JDBC drivers
	 *  @param dbUrl connection string
	 *  @param usr  user name
	 *  @param passwd password
	 *  @throws  SQLException
	 */

//	public DbDAO(String dbUrl, String usr, String passwd) throws SQLException {
//		if (dbUrl == null) {
//			System.out.println("DbDAO constructor: replacing null dbUrl with "+H2_URL);
//			dbUrl = H2_URL; // default to H2, an embedded DB
//			usr = "test";
//			passwd = "";
//		} else {
//			System.out.println("DbDAO constructor called with "+dbUrl);
//		}
//		
//		// Although simple JDBC apps no longer need Class.forName lookups, we are
//		// using a jar with all three drivers in it and this confuses the
//		// automatic driver location by JDBC. So we do it the old way.
//		String dbDriverName;
//		if (dbUrl.contains("mysql"))
//			dbDriverName = MYSQL_DRIVER;
//		else if (dbUrl.contains("oracle"))
//			dbDriverName = ORACLE_DRIVER;
//		else if (dbUrl.contains("h2"))
//			dbDriverName = H2_DRIVER;
//		else throw new SQLException("Unknown DB URL "+dbUrl);		
//	
//		try {
//			Class.forName(dbDriverName);   // as done with JDBC before v4
//		} catch (Exception e) {
//			System.out.println("can't find driver " + dbDriverName);
//		}
//		connection = DriverManager.getConnection(dbUrl, usr, passwd);
//	}

	
	/**
	*  bring DB back to original state
	*  @throws  SQLException
	**/
	public void initializeDb(Connection connection) throws SQLException {
		clearTable(connection, DOWNLOAD_TABLE);
		clearTable(connection, LINEITEM_TABLE);
		clearTable(connection, INVOICE_TABLE);
		clearTable(connection, USER_TABLE);
		clearTable(connection, SYS_TABLE);
		initSysTable(connection);		
	}

	/**
	*  Delete all records from the given table
	*  @param tableName table name from which to delete records
	*  @throws  SQLException
	**/
	public void clearTable(Connection connection, String tableName) throws SQLException {
		Statement stmt = connection.createStatement();
		try {
			stmt.execute("delete from " + tableName);
		} finally {
			stmt.close();
		}
	}
	
	/**
	*  Set all the index number used in other tables back to 1
	*  @throws  SQLException
	**/
	private void initSysTable(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		try {
			stmt.execute("insert into " + SYS_TABLE + " values (1,1,1,1)");
		} finally {
			stmt.close();
		}
	}
	public Connection startTransaction() throws SQLException {
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(false);
		return connection;
	}

	public void commitTransaction(Connection connection) throws SQLException {
		// the commit call can throw, and then the caller needs to rollback
		connection.commit();
		connection.close();
	}

	public void rollbackTransaction(Connection connection) throws SQLException {
		connection.rollback();
		connection.close();
	}
	
	// If the caller has already seen an exception, it probably
	// doesn't want to handle a failing rollback, so it can use this.
	// Then the caller should issue its own exception based on the
	// original exception.
	public void rollbackAfterException(Connection connection) {
		try {
			rollbackTransaction(connection);
		} catch (Exception e) {	
			// discard secondary exception--probably server can't be reached
		}
		try {
			connection.close();
		} catch (Exception e) {
			// discard secondary exception--probably server can't be reached
		}
	}
}
