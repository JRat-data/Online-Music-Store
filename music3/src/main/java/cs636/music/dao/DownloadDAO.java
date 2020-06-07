
package cs636.music.dao;

import static cs636.music.dao.DBConstants.SYS_TABLE;
import static cs636.music.dao.DBConstants.DOWNLOAD_TABLE;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cs636.music.domain.Download;

/**
 * 
 * Access Download table through this class. 
 */
@Repository
public class DownloadDAO {
	@Autowired
	private ProductDAO proddb;
	@Autowired
	private UserDAO userdb;

	/**
	 * An Data Access Object for Download table
	 * @param db the database connection
	 * @param user_db using the site user table 
	 * @throws SQLException
	 */
//	public DownloadDAO(ProductDAO product_db ){
//		proddb = product_db;
//	}
	
	/**
	 * Increase download_id by 1 in the system table
	 * @throws SQLException
	 */
	private void advanceDownloadID(Connection connection) throws SQLException
	{
		Statement stmt = connection.createStatement();
		try {
			stmt.executeUpdate(" update " + SYS_TABLE
					+ " set download_id = download_id + 1");
		} finally {
			stmt.close();
		}
	}
	
	/**
	 * Get the available download id 
	 * @return the download id available 
	 * @throws SQLException
	 */
	private int getNextDownloadID(Connection connection) throws SQLException
	{
		int nextDID;
		Statement stmt = connection.createStatement();
		try {
			ResultSet set = stmt.executeQuery(" select download_id from " + SYS_TABLE);
			set.next();
			nextDID = set.getInt("download_id");
		} finally {
			stmt.close();
		}
		advanceDownloadID(connection); // the id has been taken, so set +1 for next one
		return nextDID;
	}
	
	/**
	 * insert a download history to download table
	 * @param download
	 * @throws SQLException
	 */
	public void insertDownload(Connection connection, Download download) throws SQLException {
		Statement stmt = connection.createStatement();
		int download_id = getNextDownloadID(connection);
		download.setDownloadId(download_id);
		try{
			String sqlString = "insert into "+ DOWNLOAD_TABLE + " values (" +
			download.getDownloadId() + ", " + 
			download.getUser().getId() + ", " + 
			"current_timestamp" +
			", " +  download.getTrack().getId() + ")" ;
			System.out.println(sqlString);
			stmt.execute(sqlString);
		} finally {
			stmt.close();
		}
	}
	
	
	/**
	 * @return all download history in a Set
	 * @throws SQLException
	 */
	public Set<Download> findAllDownloads(Connection connection)throws SQLException {
		Download download=null;
		Set<Download> downloads = new HashSet<Download>();
		Statement stmt = connection.createStatement();
		String sqlString = "select * from "+ DOWNLOAD_TABLE + 
		     " order by download_date";
		try{
			ResultSet set = stmt.executeQuery(sqlString);
			while (set.next()){
				download = new Download();
				download.setDownloadId(set.getInt("download_id"));
				download.setDownloadDate(set.getTimestamp("download_date"));
				download.setTrack(proddb.findTrackByTID(connection,set.getInt("track_id")));
				download.setUser(userdb.findUserByID(connection, set.getInt("user_id")));
				downloads.add(download);
			}
		} finally{
			stmt.close();
		}
		return downloads;
	}
}
