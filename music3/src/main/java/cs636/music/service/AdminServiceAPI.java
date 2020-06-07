package cs636.music.service;

import java.util.Set;

import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;

public interface AdminServiceAPI {

	public void initializeDB() throws ServiceException;
	
	/**
	 * process the invoice
	 * @param invoice_id
	 * @throws ServiceException
	 */	
	public void processInvoice(long invoice_id) throws ServiceException;
	
	/**
	 * Get a list of all invoices
	 * @return list of all invoices
	 * @throws ServiceException
	 */
	
	public Set<InvoiceData> getListofInvoices() throws ServiceException;
	/**
	 * Get a list of all unprocessed invoices
	 * @return list of all unprocessed invoices
	 * @throws ServiceException
	 */
	public Set<InvoiceData> getListofUnprocessedInvoices() throws ServiceException;
	
	/**
	 * Get a list of all downloads, including important track, product, and user details
	 * @return list of all downloads
	 * @throws ServiceException
	 */
	public Set<DownloadData> getListofDownloads() throws ServiceException;
	
	/**
	 * Check login user
	 * @param username
	 * @param password
	 * @return true if useranme and password exist, otherwise return false
	 * @throws ServiceException
	 */
	public Boolean checkLogin(String username,String password) throws ServiceException;
}
