package jd.model;

public class Item {
	private Integer item_id = 0;
	private String item_name = "";
	private Float item_price = 0.00f;
	private Integer quantity_in_stock = 0;
	/**
	 * @param item_id
	 * @param item_name
	 * @param item_price
	 * @param quantity_in_stock
	 */
	public Item(Integer item_id, String item_name, Float item_price, Integer quantity_in_stock) {
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_price = item_price;
		this.quantity_in_stock = quantity_in_stock;
	}
	
	public Item() {
		
	}

	public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public Float getItem_price() {
		return item_price;
	}

	public void setItem_price(Float item_price) {
		this.item_price = item_price;
	}

	public Integer getQuantity_in_stock() {
		return quantity_in_stock;
	}

	public void setQuantity_in_stock(Integer quantity_in_stock) {
		this.quantity_in_stock = quantity_in_stock;
	}
	
	
}
