package cs636.music.dao;
//Example JUnit4 test 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cs636.music.domain.Download;
import cs636.music.domain.Product;
import cs636.music.domain.User;

@RunWith(SpringRunner.class)
//Needed to handle DataSource config
@JdbcTest
// To be minimalistic, configure only the needed beans
@ContextConfiguration(classes= {DbDAO.class, DownloadDAO.class,ProductDAO.class, UserDAO.class})
// Use application-test.properties in src/main/resources instead of application.properties
@ActiveProfiles("test")
public class DownloadDAOTest {
	@Autowired
	private DbDAO dbDAO;
	@Autowired
	private DownloadDAO downloaddao;
	@Autowired
	private ProductDAO productdao;
	@Autowired
	private UserDAO userdao;
    private User user;  // set up in setup()
    private Connection connection = null;

	@Before
	// each test runs in its own transaction, on same db setup
	public void setup() throws SQLException {
		Connection connection = dbDAO.startTransaction();
		dbDAO.initializeDb(connection); 
		dbDAO.commitTransaction(connection);
		connection = dbDAO.startTransaction();
	}

	@After
	public void tearDown() {
		dbDAO.rollbackAfterException(connection);
	}
	
//	@Test
//	public void testInsertDownload() throws SQLException
//	{
//		Product p = productdao.findProductByCode(connection, "8601");
//		
//		user = new User();
//		user.setEmailAddress("doe@joe.com");
//		user.setFirstname("doe");
//		user.setLastname("schmo");
//		userdao.insertUser(connection, user);
//		User u = userdao.findUserByEmail(connection, "doe@joe.com");
//		System.out.println("got back user "+ u.getEmailAddress());
//		userdao.insertUser(connection, u);
//		Download d = new Download();
//		d.setDownloadDate(new Date());
//		d.setUser(user);
//		d.setTrack(p.getTracks().iterator().next());
//		
//		downloaddao.insertDownload(connection, d);
//		dbDAO.commitTransaction(connection);
//	}
	
	@Test
	public void testFindAllDownloads() throws SQLException {
		Connection connection = dbDAO.startTransaction();
		Product p = productdao.findProductByCode(connection, "8601");
		user = new User();
		user.setEmailAddress("doe@joe.com");
		user.setFirstname("doe");
		user.setLastname("schmo");
		userdao.insertUser(connection, user);	
		User u = userdao.findUserByEmail(connection, "doe@joe.com");
		System.out.println("got back user "+ u.getEmailAddress());
		Download d = new Download();
		d.setDownloadDate(new Date());
		d.setUser(u);
		d.setTrack(p.getTracks().iterator().next());
		downloaddao.insertDownload(connection, d);
		
		Set<Download> downloads = downloaddao.findAllDownloads(connection);
		assertTrue(downloads.size()==1);
	//	assertEquals("doe@joe.com", downloads.iterator().next().getUser().getEmailAddress());
		dbDAO.commitTransaction(connection);

	}
}
