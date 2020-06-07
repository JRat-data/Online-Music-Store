package cs636.music.presentation.client;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

import cs636.music.service.AdminService;
import cs636.music.service.ServiceException;
import cs636.music.service.data.InvoiceData;

/**
 * 
 * Line-oriented client code for Music administrator user. To be replaced by JSP pages
 * later. 
 * 
 */
/**
 * @author Chung-Hsine Yu
 * 
 */
public class AdminApp {

	private AdminService adminService;
	private Scanner in;
	
	public AdminApp(AdminService adminService) {
		this.adminService = adminService;
		in = new Scanner(System.in);
	}	
	
	public void loginPage() throws IOException, ServiceException {
		System.out.println("---Login Page---");
		String username = PresentationUtils.readEntry(in,"User Name _ ");
		String password = PresentationUtils.readEntry(in,"Password _ ");
		if (adminService.checkLogin(username, password)){
			handleMainPage();
		} else {
			System.out.println(" User name and password did not match !!");
		}
	}
	
	public void handleMainPage() throws IOException, ServiceException {
		String command = null;
		while (true) {
			System.out.println("---Main Page---");
			System.out.println(" P: Process Invoice ");
			System.out.println(" R: Display Report ");
			System.out.println(" I: Initialize Database ");
			System.out.println(" Q: Quit ");
			command = PresentationUtils.readEntry(in,"Please Enter the Command");
			if (command.equalsIgnoreCase("P")) {
				this.processInvoice();
			} else if (command.equalsIgnoreCase("R")) {
				this.displayReports();
			} else if (command.equalsIgnoreCase("I")) {
				this.adminService.initializeDB();
			} else if (command.equalsIgnoreCase("Q")) {
				return;
			} else {
				System.out.println("Invalid Command! Try again...");
			}
		}
	}
	
	public void processInvoice() throws IOException, ServiceException {
		String command = null;
		while (true) {
			System.out.println("---Process Invoice Page---");
			Set<InvoiceData> invoices = adminService.getListofUnprocessedInvoices();
			if (invoices.isEmpty()) {
				System.out.println("No unprocessed invoices exist, returning to main page");
				return;
			}
			PresentationUtils.displayInvoices(this.adminService.getListofUnprocessedInvoices(), System.out);
			System.out.println(" I: Choose Invoice to Process");
			System.out.println(" B: Back to Main Page ");
			command = PresentationUtils.readEntry(in,"Please Enter the Command");
			if (command.equalsIgnoreCase("I")) {
				int invoice_id = Integer.parseInt(PresentationUtils.readEntry(in, "invoice id"));
				System.out.println(" Processing invoice " + invoice_id + " .....");
				this.adminService.processInvoice(invoice_id);
			} else if (command.equalsIgnoreCase("B")) {
				return;
			} else {
				System.out.println("Invalid Command! Try again...");
			}
		}
	}
	
	public void displayReports() throws IOException, ServiceException {
		String command = null;
		while (true) {
			System.out.println("---Display Reports Page---");
			System.out.println(" I: List of All Invoices To Process");
			System.out.println(" D: List of All Downloads");
			System.out.println(" B: Back to Main Page ");
			command = PresentationUtils.readEntry(in,"Please Enter the Command");
			if (command.equalsIgnoreCase("I")) {
				PresentationUtils.displayInvoices(this.adminService.getListofInvoices(),System.out);
			} else if (command.equalsIgnoreCase("D")) {	
				PresentationUtils.downloadReport(adminService.getListofDownloads(), System.out);
			} else if (command.equalsIgnoreCase("B")) {
				return;
			} else {
				System.out.println("Invalid Command! Try again...");
			}
		}
	}
}
