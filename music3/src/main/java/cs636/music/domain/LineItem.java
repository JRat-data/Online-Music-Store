package cs636.music.domain;

import java.math.BigDecimal;

/**
 * LineItem: like Murach, pg. 649, except:
 * --The Product field is called "product", not "item"
 * --The database id is exposed with getter/setter
 * --getTotal in Murach is called calculateItemTotal here
 * to signify it is not a table attribute.
 *
 */
public class LineItem {

	private long id;
	private Product product;
	private int quantity;
	// no-args constructor, to be proper JavaBean
	public LineItem() {}
	
	// for DAO use: LineItem from DB
	public LineItem(long id, Product product, Invoice invoice, int quantity) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
	}
	
	// for service layer
	public LineItem(Product product, Invoice invoice, int quantity) {
		this.product = product;
		this.quantity = quantity;		
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getId() {
		return id;
	}

	public void setId(long lineitem_id) {
		this.id = lineitem_id;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	// Note this is method returning a quantity that is not a database column
	// value from the lineitem table. To avoid problems later, we avoid "getXXX" 
	// naming for such methods.
	// Also, the name points out this is not just a value from the table.

	public BigDecimal calculateItemTotal() {
		// We can't use * to multiply with BigDecimal, but it knows how--
		BigDecimal total = product.getPrice().multiply(new BigDecimal(quantity));
		return total;
	}

}
