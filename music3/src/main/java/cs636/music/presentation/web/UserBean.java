// This is the session bean (POJO) for the user-oriented web pages
// It is created in the UserWelcomeController, and if the user happens
// on another page first, the user is forwarded to that page.
// This bean holds information for the user across the various
// page visits.
package cs636.music.presentation.web;

import cs636.music.domain.Cart;
import cs636.music.service.data.UserData;

public class UserBean {
	// Here we have gathered together all the needed session variables
	// but these could all be separate session variables, or hybrid scheme
	private UserData loggedInUser = null; 
	// Need to remember product across registration for listening to samples
	private String productCode;  // chosen product for product page, sound page
	private Cart cart = null;
	// Need to remember we're doing checkout through registration step
	private boolean doingCheckout = false;  // set by beginCheckout, cleared by checkout

	public UserBean() {}
	
	public UserData getUser() {
		return loggedInUser;
	}

	public void setUser(UserData user) {
		this.loggedInUser = user;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public boolean isDoingCheckout() {
		return doingCheckout;
	}

	public void setDoingCheckout(boolean doingCheckout) {
		this.doingCheckout = doingCheckout;
	}
}
