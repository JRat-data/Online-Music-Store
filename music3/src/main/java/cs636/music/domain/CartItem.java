package cs636.music.domain;

/**
 * CartItem: memory-only object for item in the cart
 * i.e, it is not a "persistent object" tied to database data
 * It is mutable, but also user private, so
 * it doesn't violate the principle of keeping
 * persistent domain objects out of the presentation layer.
 * Note that the product object, a persistent object is
 * only represented by its id, not the whole object.
 * Data is moved to a LineItem object in checkout
 * For API calls, see related POJO service.data/CartItemData.java
 *
 */
public class CartItem {

	private long productId;
	private int quantity;
	
	// no-args constructor, to be proper JavaBean
	public CartItem() {}
	
	public CartItem (long productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public long getProductId() {
		return productId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
