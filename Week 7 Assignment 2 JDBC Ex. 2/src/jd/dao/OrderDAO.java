package jd.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jd.model.Item;
import jd.utils.OracleQueries;

public class OrderDAO {
	
	private Map<Integer, Integer> cart = new HashMap<Integer, Integer>();
	
	
	public Map<Integer, Integer> getCart() {
		return cart;
	}

	public void setCart(Map<Integer, Integer> cart) {
		this.cart.putAll(cart);
	}

	public boolean createOrder(Map<Integer, Integer> order) {
		Boolean isCreated = false;
		Connection conn= null;
		PreparedStatement stmt = null;
		ItemDAO iDAO = new ItemDAO();
		Boolean isQuantityUpdated = false;
		
		try {
			conn = OracleConnection.getConnection();
			stmt = conn.prepareStatement(OracleQueries.CREATEORDER);
			
			//int count = 0;
			
			for(Map.Entry<Integer, Integer> entry : order.entrySet()) {
				stmt.setInt(1, entry.getKey());
				stmt.setInt(2, entry.getValue());
				
				//Updating quantity in stock when i order is created
				Integer quantityInStock = getStockQuantity(entry.getKey());
				Integer quantityRemaining = quantityInStock - entry.getValue();
				isQuantityUpdated = iDAO.updateQuantityInStock(entry.getKey(), quantityRemaining);
				
				stmt.addBatch();
				//count++;
			}
			
			if(isQuantityUpdated == true) {
				//Checking return value of update count
				//If executeUpdate returns value greater than 0 updateCount becomes true
				int[] updateCount = stmt.executeBatch();
				
				if(updateCount.length > 0) {
					isCreated = true;
					//System.out.println(updateCount.length);
				}
			}
			
		}catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isCreated;
	}
	
	public Boolean addToCart(Integer item_id, Integer quantity) throws SQLException {
		
		Map<Integer, Integer> cartTemp = new HashMap<Integer, Integer>();
		Boolean isAddToCart = false;
		
		Integer quantityInStock = getStockQuantity(item_id);
		
		if(quantity <= quantityInStock) {
			if(!cartTemp.isEmpty()) {
				if(this.getCart().containsKey(item_id)) {
					int tQuantity = this.getCart().get(item_id).intValue() + quantity;
					
					if(tQuantity <= quantityInStock) {
						cartTemp.put(item_id, tQuantity);
						isAddToCart = true;
					}
				}else {
					cartTemp.put(item_id, quantity);
					isAddToCart = true;
				}
			}else {
				cartTemp.put(item_id, quantity);
				isAddToCart = true;
			}
			
			this.setCart(cartTemp);
		}
		
		return isAddToCart;
	}
	
	public Integer getStockQuantity(Integer item_id) throws SQLException {
		ItemDAO items = new ItemDAO();
		List<Item> allItems = items.getItemsInStock();
		Integer actualQuantity = 0;
		
		for(Item item : allItems) {
			if(item.getItem_id() == item_id) {
				actualQuantity = item.getQuantity_in_stock();
			}
		}
		
		return actualQuantity;
	}
}
