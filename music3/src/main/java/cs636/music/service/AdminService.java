package cs636.music.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs636.music.dao.AdminDAO;
import cs636.music.dao.DbDAO;
import cs636.music.dao.DownloadDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;

/**
 * 
 * Provide admin level services to user app through accessing DAOs 
 * @author Chung-Hsien (Jacky) Yu
 */
@Service
public class AdminService {
	@Autowired	
	private DbDAO db;
	@Autowired
	private DownloadDAO downloadDb;
	@Autowired
	private InvoiceDAO invoiceDb;
	@Autowired
	private AdminDAO adminDb;
	
	/**
	 * construct a admin service provider 
	 * @param dbDao
	 * @param downloadDao
	 * @param invoiceDao
	 */
//	public AdminService(DbDAO dbDao, DownloadDAO downloadDao ,InvoiceDAO invoiceDao, AdminDAO adminDao) {
//		db = dbDao;
//		downloadDb = downloadDao;
//		invoiceDb = invoiceDao;
//		adminDb = adminDao;
//		
//	}
	
	/**
	 * Clean all user table, not product and system table to empty
	 * and then set the index numbers back to 1
	 * @throws ServiceException
	 */
	public void initializeDB()throws ServiceException {
		Connection connection = null;
		try {
			connection = db.startTransaction();
			db.initializeDb(connection);
			db.commitTransaction(connection);
		} catch (Exception e) { // any exception
			// the following doesn't itself throw, but it handles the case that
			// rollback throws, discarding that exception object
			db.rollbackAfterException(connection);
			throw new ServiceException(
					"Can't initialize DB: (probably need to load DB)", e);
		}
	}
	
	/**
	 * process the invoice
	 * @param invoiceId
	 * @throws ServiceException
	 */
	public void processInvoice(long invoiceId) throws ServiceException {
		Connection connection = null;
		try {
			connection = db.startTransaction();
			Invoice i = invoiceDb.findInvoice(connection, invoiceId);
			invoiceDb.updateInvoice(connection, i);
			db.commitTransaction(connection);
		} catch (SQLException e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Can't process invoice: ", e);
		}
	}

	/**
	 * Get a list of all invoices
	 * @return list of all invoices in InvoiceData objects
	 * @throws ServiceException
	 */
	public Set<InvoiceData> getListofInvoices() throws ServiceException {
		Connection connection = null;
		Set<Invoice> invoices = null;
		try {
			connection = db.startTransaction();
			invoices = invoiceDb.findAllInvoices(connection);
			db.commitTransaction(connection);
		} catch (Exception e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Can't find invoice list in DB: ", e);
		}
		Set<InvoiceData> invoices1 = new HashSet<InvoiceData>();
		for (Invoice i : invoices) {
			invoices1.add(new InvoiceData(i));
		}
		return invoices1;
	}
	
	/**
	 * Get a list of all unprocessed invoices
	 * @return list of all unprocessed invoices in InvoiceData objects
	 * @throws ServiceException
	 */
	public Set<InvoiceData> getListofUnprocessedInvoices() throws ServiceException {
		Connection connection = null;
		Set<Invoice> invoices = null;
		try {
			connection = db.startTransaction();
			invoices = invoiceDb.findAllUnprocessedInvoices(connection);
			db.commitTransaction(connection);
		} catch (SQLException e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Can't find unprocessed invoice list in DB: ", e);
		}
		Set<InvoiceData> invoices1 = new HashSet<InvoiceData>();
		for (Invoice i : invoices) {
			invoices1.add(new InvoiceData(i));
		}
		return invoices1;
	}
	
	/**
	 * Get a list of all downloads
	 * @return list of all downloads as DownloadData objects
	 * @throws ServiceException
	 */
	public Set<DownloadData> getListofDownloads() throws ServiceException {
		Connection connection = null;		
		Set<Download> downloads = null;
		try {
			connection = db.startTransaction();
			downloads =  downloadDb.findAllDownloads(connection);
			db.commitTransaction(connection);
		} catch (SQLException e)
		{
			db.rollbackAfterException(connection);
			throw new ServiceException("Can't find download list: ", e);
		}
		Set<DownloadData> downloads1 = new HashSet<DownloadData>();
		for (Download d: downloads) {
			downloads1.add(new DownloadData(d));
		}
		return downloads1;
	}
	
	
	/**
	 * Check login user
	 * @param username
	 * @param password
	 * @return true if useranme and password exist, otherwise return false
	 * @throws ServiceException
	 */
	public Boolean checkLogin(String username,String password) throws ServiceException {
		Connection connection = null;
		try {
			connection = db.startTransaction();
			Boolean b = adminDb.findAdminUser(connection, username, password);
			db.commitTransaction(connection);
			return b;
		} catch (Exception e)
		{
			db.rollbackAfterException(connection);
			throw new ServiceException("Check login error: ", e);
		}
	}
	
}
