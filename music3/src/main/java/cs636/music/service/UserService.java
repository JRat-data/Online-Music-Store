package cs636.music.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs636.music.dao.DbDAO;
import cs636.music.dao.DownloadDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.dao.ProductDAO;
import cs636.music.dao.UserDAO;
import cs636.music.domain.Cart;
import cs636.music.domain.CartItem;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.domain.LineItem;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;
import cs636.music.service.data.CartItemData;
import cs636.music.service.data.InvoiceData;
import cs636.music.service.data.UserData;

/**
 * 
 * Provide user level services to user app through accessing DAOs
 * 
 */
@Service
public class UserService {
	@Autowired
	private DownloadDAO downloadDb;
	@Autowired
	private InvoiceDAO invoiceDb;
	@Autowired
	private ProductDAO prodDb;
	@Autowired
	private UserDAO userDb;
	@Autowired
	private DbDAO db;

	/**
	 * construct a user service provider
	 * 
	 * @param productDao
	 * @param userDao
	 * @param downloadDao
	 * @param lineItemDao
	 * @param invoiceDao
	 */
//	public UserService(ProductDAO productDao, UserDAO userDao,
//			DownloadDAO downloadDao, InvoiceDAO invoiceDao, DbDAO dbdao) {
//		downloadDb = downloadDao;
//		invoiceDb = invoiceDao;
//		prodDb = productDao;
//		userDb = userDao;
//		this.db = dbdao;
//	}

	/**
	 * Getting list of all products
	 * 
	 * @return list of all product
	 * @throws ServiceException
	 */
	public Set<Product> getProductList() throws ServiceException {
		Connection connection = null;
		Set<Product> prodlist;
		try {
			connection = db.startTransaction();
			prodlist = prodDb.findAllProducts(connection);
			db.commitTransaction(connection);
		} catch (SQLException e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Can't find product list in db: ", e);
		}
		return prodlist;
	}

	/**
	 * Create a new cart
	 * 
	 * @return the cart
	 */
	public Cart createCart() {
		return new Cart();
	}
	
	/**
	 * Get cart info
	 * 
	 * @return the cart's content
	 * API design: OK to use Set<CartItem> here for return value, but then need getProductById in service API
	 *     because CartItem only has the product id, not the whole Product 
	 */
	public Set<CartItemData> getCartInfo(Cart cart) throws ServiceException {
		Connection connection = null;
		Set<CartItemData> items = new HashSet<CartItemData>();
		try {
			connection = db.startTransaction();
			for (CartItem item : cart.getItems()) {
				Product product = prodDb.findProductByPID(connection, item.getProductId());
				CartItemData itemData = new CartItemData(item.getProductId(), item.getQuantity(), product.getCode(),
						product.getDescription(), product.getPrice());
				items.add(itemData);
			}
			db.commitTransaction(connection);
		} catch (Exception e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Can't find product in cart: ", e);
		}
		return items;
	}

	/**
	 * Add a product to the cart. If the product is already in the cart, add
	 * quantity. Otherwise, insert a new line item.
	 * 
	 * @param prod
	 * @param cart
	 * @param quantity
	 */
	public void addItemtoCart(Product prod, Cart cart, int quantity) {
		CartItem item = cart.findItem(prod.getId());
		if (item != null) { // product is already in the cart, add quantity
			int qty = item.getQuantity();
			item.setQuantity(qty + quantity);
		} else { // not in the cart, add new item with quantity
			item = new CartItem(prod.getId(), quantity);
			// cart.addItem(item);
			cart.getItems().add(item);
		}
	}

	/**
	 * Change the quantity of one item. If quantity <= 0 then delete this item.
     * API design: first argument can be productId here (good choice actually)
	 * @param prod
	 * @param cart
	 * @param quantity
	 */
	public void changeCart(Product prod, Cart cart, int quantity) {
		CartItem item = cart.findItem(prod.getId());
		if (item != null) {
			if (quantity > 0) {
				item.setQuantity(quantity);
			} else { // if the quantity was changed to 0 or less, remove the
						// product from cart
				cart.removeItem(prod.getId());
			}
		}
	}

	/**
	 * Remove a product item from the cart
	 * API design: first argument can be productId here (good choice actually)
	 * 
	 * @param prod
	 * @param cart
	 */
	public void removeCartItem(Product prod, Cart cart) {
		CartItem item = cart.findItem(prod.getId());
		if (item != null) {
			cart.removeItem(prod.getId());
		}
	}

	/**
	 * Register user if the email does not exist in the db
	 * 
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @throws ServiceException
	 */
	public void registerUser(String firstname, String lastname, String email)
			throws ServiceException {
		Connection connection = null; 
		User user = null;

		try {
			connection = db.startTransaction();
			user = userDb.findUserByEmail(connection, email);
			if (user == null) { // this user has not registered yet
				user = new User();
				user.setFirstname(firstname);
				user.setLastname(lastname);
				user.setEmailAddress(email);
				userDb.insertUser(connection, user);
			}
			db.commitTransaction(connection);
		} catch (SQLException e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Error while registering user: ", e);
		}
	}

	/**
	 * Get user info by given email address
	 * Note: here we assume the presentation layer received the user email
	 * string (the argument here) from the user.
	 * 
	 * @param email
	 * @return the user info found, return null if not found
	 * @throws ServiceException
	 */
	public UserData getUserInfo(String email) throws ServiceException {
		Connection connection = null;
		User user = null;
		UserData user1 = null;
		try {
			connection = db.startTransaction();
			user = userDb.findUserByEmail(connection, email);
			user1 = new UserData(user);
			db.commitTransaction(connection);
		} catch (SQLException e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Error while getting user info: ", e);
		}
		return user1;
	}

	/**
	 * Return a product info by given product code
	 * 
	 * @param prodCode
	 *            product code
	 * @return the product info
	 * @throws ServiceException
	 */
	public Product getProduct(String prodCode) throws ServiceException {
		Connection connection = null;
		Product prd = null;
		try {
			connection = db.startTransaction();
			prd = prodDb.findProductByCode(connection, prodCode);
			db.commitTransaction(connection);
		} catch (SQLException e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Error while registering user: ", e);
		}
		return prd;
	}

	/**
	 * Check out the cart from the user order and then generate an invoice for
	 * this order. Empty the cart after
	 * 
	 * @param cart
	 * @param user id
	 * @return new invoice
	 * @throws ServiceException
	 */
	public InvoiceData checkout(Cart cart, long userId) throws ServiceException {
		Connection connection = null;
		Invoice invoice = null;
		try {
			connection = db.startTransaction();
			User user = userDb.findUserByID(connection, userId);
			invoice = new Invoice(-1, user, new Date(), false, null, null);
			Set<LineItem> lineItems = new HashSet<LineItem>();
			for (CartItem item : cart.getItems()) {  
				Product prod = prodDb.findProductByPID(connection, item.getProductId());
				LineItem li = new LineItem(prod, invoice, item.getQuantity());
				lineItems.add(li);
			}
			invoice.setLineItems(lineItems);
			invoice.setTotalAmount(invoice.calculateTotal());
			invoiceDb.insertInvoice(connection, invoice);
			db.commitTransaction(connection);
		} catch (SQLException e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Can't check out: ", e);
		}
		cart.clear();
		return new InvoiceData(invoice);
	}

	/**
	 * Add one download history, record the user and track
	 * 
	 * @param userId
	 *            id of user who downloaded the track
	 * @param track
	 *            the track which was downloaded
	 * @throws ServiceException
	 */
	public void addDownload(long userId, Track track) throws ServiceException {
		Connection connection = null;
		try {
			connection = db.startTransaction();
			Download download = new Download();
			User user = userDb.findUserByID(connection, userId);
			download.setUser(user);
			download.setTrack(track);
			downloadDb.insertDownload(connection, download);  // let database determine timestamp
			db.commitTransaction(connection);
		} catch (SQLException e) {
			db.rollbackAfterException(connection);
			throw new ServiceException("Can't add download: ", e);
		}
	}
}
