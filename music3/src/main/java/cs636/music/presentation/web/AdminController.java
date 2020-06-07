package cs636.music.presentation.web;

import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cs636.music.config.MusicSystemConfig;
import cs636.music.service.AdminService;
import cs636.music.service.ServiceException;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;
// For partnerships: finish this controller code and its JSPs
// for individual projects: Just leave this as is, admins can use AdminApp

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService; 

	private static final String ADMIN_BASE_URL = "/adminController/";
	private static final String ADMIN_JSP_DIR = "/WEB-INF/admin/";

	@RequestMapping(ADMIN_BASE_URL + "adminWelcome.html")
	public String handleAdminLogin(Model model, @RequestParam(required = false) String username,
			@RequestParam(required = false) String password, HttpServletRequest request) throws ServletException {
		String uname = (String) request.getSession().getAttribute("adminUser");
		if (uname != null) {
			return ADMIN_JSP_DIR + "adminMenu"; // already logged in
		}

		if (username != null && password != null) {
			try {
				Boolean adminExistence = adminService.checkLogin(username, password);
				// Success: Need to transfer the admin to the Admin Menu page
				// Bad credentials: Need to make the admin stay at the same login page.
				// Inform user 'Invalid Credentials'.
				if (adminExistence) {
					// save uname as session variable
					request.getSession().setAttribute("adminUser", username);
					return ADMIN_JSP_DIR + "adminMenu";
				} else {
					request.setAttribute("error", "Invalid Credentials");
					return ADMIN_JSP_DIR + "adminLogin";
				}
			} catch (ServiceException e) {
				String error = "Error in checking credentials: " + e;
				request.setAttribute("error", error);
				return ADMIN_JSP_DIR + "error";
			}
		}
		// missing or incomplete username/password: show login page
		else {
			return ADMIN_JSP_DIR + "adminLogin";
		}
	}
	
	@RequestMapping(ADMIN_BASE_URL+"listVariables.html")
	public String listVariables(Model model) {	
		String url = ADMIN_JSP_DIR + "listVariables";
		return url;
	}
	
	@RequestMapping(ADMIN_BASE_URL + "logout.html")
	public String logout(Model model, HttpServletRequest request) {
		request.getSession().invalidate(); // drop session
		String url = ADMIN_JSP_DIR + "logout";
		return url;
	}

	@RequestMapping(ADMIN_BASE_URL + "processInvoices.html")
	public String processInvoices(Model model, HttpServletRequest request) throws ServletException {
		if (!checkAdmin(request))
			return "forward:" + ADMIN_BASE_URL + "adminWelcome.html";
		// TODO code, other url
		String url = ADMIN_JSP_DIR + "error";
		return url;
	}

	@RequestMapping(ADMIN_BASE_URL + "initDB.html")
	public String initializeDB(Model model, HttpServletRequest request) throws ServletException {
		if (!checkAdmin(request))
			return "forward:" + ADMIN_BASE_URL + "adminWelcome.html";
		// TODO code
		String url = ADMIN_JSP_DIR + "initializeDB";
		return url;
	}

	@RequestMapping(ADMIN_BASE_URL + "displayReports.html")
	public String displayReports(Model model, HttpServletRequest request) throws ServletException {
		if (!checkAdmin(request))
			return "forward:" + ADMIN_BASE_URL + "adminWelcome.html";
		// TODO code, other url
		String url = ADMIN_JSP_DIR + "error";
		return url;
	}

	@RequestMapping(ADMIN_BASE_URL + "processInvoice.html")
	public String processInvoice(Model model, @RequestParam() long invoiceId, HttpServletRequest request)
			throws ServletException {
		if (!checkAdmin(request))
			return "forward:" + ADMIN_BASE_URL + "adminWelcome.html";
		//TODO code
		return "forward:/adminController/processInvoices.html";
	}

	boolean checkAdmin(HttpServletRequest request) {
		boolean isAdmin = (request.getSession().getAttribute("adminUser") != null);
		return isAdmin;
	}
}
