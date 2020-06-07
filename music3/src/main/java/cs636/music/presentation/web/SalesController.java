package cs636.music.presentation.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import cs636.music.domain.Cart;
import cs636.music.service.ServiceException;
import cs636.music.service.UserService;
import cs636.music.service.data.InvoiceData;
import cs636.music.service.data.UserData;

@Controller
public class SalesController {
	@Autowired
	private UserService userService;
		
	// String constants for URLs
	private static final String USER_WELCOME_URL = "/userWelcome.html";
	private static final String USER_WELCOME_VIEW = "/WEB-INF/jsp/userWelcome";
	private static final String LISTEN_URL = "/listen.html";
	
	private static final String REGISTER_URL = "/register.html";
	private static final String BEGIN_CHECKOUT_URL = "/beginCheckout.html";
	private static final String CHECKOUT_URL = "/checkout.html";
	private static final String INVOICE_VIEW = "/WEB-INF/jsp/invoice";
	private static final String REGISTER_FORM_VIEW = "/WEB-INF/jsp/registerForm";

	@RequestMapping(USER_WELCOME_URL)
	public String userWelcome(HttpServletRequest request) throws ServletException {
		
		HttpSession session = request.getSession();	
		// See if this user already has a bean.  If not, put one in session.
		UserBean userBean = (UserBean) session.getAttribute("user");
		if (userBean == null) {
			userBean = new UserBean();
			session.setAttribute("user", userBean);
		}
		return USER_WELCOME_VIEW;
	}
	
	// This method is meant to be invoked in two ways:
	//  -- to handle a request from the checkout page 
	//        in this case the UserBean has isDoingCheckout == true
	//        and this forwards to the checkout handler in this controller
	//  -- to handle a request from the listen page 
	//        in this case the UserBean has isDoingCheckout == false
	//        and this forwards to the listen handler in the CatalogController
	@RequestMapping(REGISTER_URL)
	public String register(@RequestParam() String firstName, @RequestParam() String lastName, 
			 @RequestParam() String email, HttpServletRequest request) throws ServletException {
		if (!checkUser(request))
			return "forward:" + USER_WELCOME_URL;

		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		UserData user = userBean.getUser();
		
		if (user == null) {
			try { // The user registered just before getting to this page - create user
				userService.registerUser(firstName, lastName, email);
				user = userService.getUserInfo(email);
				userBean.setUser(user); 
				System.out.println("user registered");
			} catch (ServiceException e) {
				System.out.println("ListenController: " + e);
				throw new ServletException(e);
			}
		}
		boolean doingCheckout = userBean.isDoingCheckout();
		System.out.println("Returning from RegisterController");
		return doingCheckout?"forward:" + CHECKOUT_URL:"forward:" + LISTEN_URL;
	}
	
	@RequestMapping(BEGIN_CHECKOUT_URL)
	public String beginCheckout(HttpServletRequest request)
			throws ServletException {
		if (!checkUser(request))
			return "forward:" + USER_WELCOME_URL;
		// Here with userBean object available, get it
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		userBean.setDoingCheckout(true);  // remember across registration if necessary
		UserData user = userBean.getUser();  // may be null: user needs to register
		System.out.println("Returning from BeginCheckoutController");
		return user != null ? "forward:" + CHECKOUT_URL: REGISTER_FORM_VIEW;
	}
	
	@RequestMapping(CHECKOUT_URL)
	public String checkout(HttpServletRequest request) throws ServletException {
		if (!checkUser(request))
			return "forward:" + USER_WELCOME_URL;
	
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		userBean.setDoingCheckout(false);  // now handled, re-initialize for future
		Cart userCart = userBean.getCart();
		UserData user = userBean.getUser();
		assert(user != null);  // should register before this if needed
		InvoiceData invoice = null;
		try {
			invoice = userService.checkout(userCart, user.getId());
		} catch (ServiceException e) {
			System.out.println("CheckoutController: " + e);
			throw new ServletException(e);
		}
		request.setAttribute("invoice", invoice);
		session.setAttribute("user", userBean);
		return INVOICE_VIEW;
	}
	
	// check for user bean, meaning user has already visited the user welcome
	// page and obtained the user bean, so known to the system
	// package scope to let SalesController use this too
	static boolean checkUser(HttpServletRequest request) {
		System.out.print("checkUser: ");
		boolean hasBean = (request.getSession().getAttribute("user") != null);
		if (hasBean) {
			UserBean bean = (UserBean) request.getSession().getAttribute("user");
			if (bean.getUser() == null)
				System.out.println("bean has no user");
			else
				System.out.println("bean's user:" + bean.getUser().getEmailAddress());
			if (bean.getCart() == null)
				System.out.println("bean has no cart");
			else
				System.out.println("bean's cart itemcount = " + bean.getCart().getItems().size());
		} else {
			System.out.println("no user bean in session");
		}
		return hasBean;
	}
}
