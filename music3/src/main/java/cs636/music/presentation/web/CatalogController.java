package cs636.music.presentation.web;

// TODO: pa2: add methods here to service URLs listed below
// which involve forwarding to JSPs listed below as VIEWs or in some cases
// to URLs

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal; // Added for subtotal in displayCart

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import cs636.music.domain.Cart;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.presentation.client.PresentationUtils;
import cs636.music.service.ServiceException;
import cs636.music.service.UserService;
import cs636.music.service.data.CartItemData;
import cs636.music.service.data.UserData;

@Controller
public class CatalogController {
	@Autowired
	private UserService userService;

	// String constants for URL's : please use these!
	private static final String WELCOME_URL = "/welcome.html";
	private static final String WELCOME_VIEW = "/welcome";
	private static final String USER_WELCOME_URL = "/userWelcome.html";
	private static final String CATALOG_URL = "/catalog.html";
	private static final String CATALOG_VIEW = "/WEB-INF/jsp/catalog";
	private static final String CART_URL = "/cart.html";
	private static final String CART_VIEW = "/WEB-INF/jsp/cart";
	private static final String ADD_URL = "/add.html";	// Added
	private static final String ADD_VIEW = "/WEB-INF/jsp/add";	// Added
	private static final String UPDATE_URL = "/update.html"; //A Added
	private static final String REMOVE_URL = "/remove.html"; // Added
	private static final String PRODUCT_URL = "/product.html";
	private static final String PRODUCT_VIEW = "/WEB-INF/jsp/product";
	private static final String LISTEN_URL = "/listen.html";
	private static final String SOUND_VIEW = "/WEB-INF/jsp/sound";
	private static final String DOWNLOAD_URL = "/download.html"; // download.html didn't work
	private static final String REGISTER_FORM_VIEW = "/WEB-INF/jsp/registerForm";

	@RequestMapping(WELCOME_URL)
	public String handleWelcome() {
		return WELCOME_VIEW;
	}
	
	@RequestMapping("")
	public String handleBlank() {
		return WELCOME_VIEW;
	}
	
	@RequestMapping(LISTEN_URL) 
	public String handleListen() {
		return "forward:" + CATALOG_URL;
	}

	@RequestMapping(CATALOG_URL)
	public String displayCatalog(HttpServletRequest request) throws ServletException {
		HttpSession session = request.getSession();	
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		Set<Product> allProducts = null;
		try {
			allProducts = userService.getProductList();
		} catch (Exception e) {
			System.out.println("exception in displayCatalog " + e);
			throw new ServletException("failed to access DB for products", e);
		}
		session.setAttribute("products", allProducts);
	
		return CATALOG_VIEW;
	}
	
	// View Cart
	@RequestMapping(CART_URL)
	public String displayCart(HttpServletRequest request) throws ServletException {
		HttpSession session = request.getSession();	
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		Cart cart = userBean.getCart();
		Set<CartItemData> cartData = null;
		BigDecimal subTotal = BigDecimal.ZERO;
		
        if (cart == null || cart.getItems().size() == 0) {
            session.setAttribute("emptyCart", "Your cart is empty");
			if(cart == null) {
				cart = new Cart();
			}
        } else {
			try {
				cartData = userService.getCartInfo(cart);
			} catch (Exception e){
				System.out.println("exception in getting cart info " + e);
				throw new ServletException(e);
			}
			if(cartData != null) {
				session.setAttribute("cartData", cartData);
			}
        }
		userBean.setCart(cart);
		session.setAttribute("user", userBean);
		
		return CART_VIEW;
	}
	
	//Based off DownloadServelt.java method determineProductView() from ch07downloadS
	//Added more cases for other products to add to cart
	@RequestMapping(ADD_URL)
	public String addProductToCart(HttpServletRequest request) throws ServletException{
		String productCode = request.getParameter("productCode");
		HttpSession session = request.getSession();	
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		// Previously had this in displayCatalog but I thought that here is more suited
		// Simply cause you shouldn't need an account to listen to sound samples but 
		// should be adding cds to a cart for checkout
		if(userBean.getUser() == null) {
			return REGISTER_FORM_VIEW;
		}
		
		Cart cart = new Cart();
		if(userBean.getCart() != null){
			cart = userBean.getCart();
		}
		Product product = null;
				
		try {
			product = userService.getProduct(productCode);
			if (product != null) {
				userService.addItemtoCart(product, cart, 1);
				userBean.setCart(cart);
			}
			session.setAttribute("productCode", productCode);
			session.setAttribute("user", userBean);
		} catch (Exception e){
			System.out.println("can't in find product " + e + " in DB");
			throw new ServletException("Error: ", e);
		}
		session.setAttribute("emptyCart", null);
		
		return ADD_VIEW;
	}
	
	
	// Based off Murach Store
	@RequestMapping(UPDATE_URL)
	private String updateItem(HttpServletRequest request)  throws ServletException{
        String quantityString = request.getParameter("quantity");
        String productCode = request.getParameter("productCode");
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
            if (quantity < 0) {
				quantity = 1;
			}
        } catch (NumberFormatException ex) {
            quantity = 1;
        }
		
		Cart cart = userBean.getCart();
        Product product = null;
		try {
			product = userService.getProduct(productCode);
			if (product != null && cart != null) {
				userService.changeCart(product, cart, quantity);
				userBean.setCart(cart);
			}
			session.setAttribute("user", userBean);
		} catch (Exception e){
			throw new ServletException("Update Error: ", e);
		}
        return "forward:" + CART_URL;
    }
    
	// Based off Murach Store
	@RequestMapping(REMOVE_URL)
    private String removeItem(HttpServletRequest request)  throws ServletException{
        HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
        Cart cart = userBean.getCart();
		if(cart == null){
			cart = new Cart();
		}
        String productCode = request.getParameter("productCode");
        Product product = null;
		
		try {
			product = userService.getProduct(productCode);
			if (product != null) {
				userService.removeCartItem(product, cart);
			}
		} catch (Exception e) {
			System.out.println("Unable to remove item " + e);
			throw new ServletException("Remove Error: ", e);
		}
		userBean.setCart(cart);
		session.setAttribute("user", userBean);
        return "forward:" + CART_URL;
    }
	
}
