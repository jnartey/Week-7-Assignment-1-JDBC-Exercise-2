package jd.model;

public class Order {
	private Integer order_id;
	private Integer item_id;
	private Integer quantity;
	
	/**
	 * @param order_id
	 * @param item_id
	 * @param quantity
	 */
	public Order(Integer order_id, Integer item_id, Integer quantity) {
		this.order_id = order_id;
		this.item_id = item_id;
		this.quantity = quantity;
	}
	
	public Order() {
		
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
