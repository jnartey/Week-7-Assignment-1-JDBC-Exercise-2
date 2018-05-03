package jd.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jd.model.Item;
import jd.utils.OracleQueries;

public class ItemDAO {
	
	public List<Item> getItemsInStock() throws SQLException {
		List<Item> list = new ArrayList<Item>();
		
		Item item = null;
		Connection conn= null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
			conn= OracleConnection.getConnection();
			stmt = conn.prepareStatement(OracleQueries.GETALLITEMSINSTOCK);
			
			// execute query is only for reading data from the database 
			result = stmt.executeQuery();
			
				while(result!= null && result.next()) {
					item = new Item();
					
					item.setItem_id(result.getInt(1));
					item.setItem_name(result.getString(2));
					item.setQuantity_in_stock(result.getInt(3));
					item.setItem_price(result.getFloat(4));
					
					list.add(item);
				}	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally {
			result.close();
			if(stmt!=null ) {
				stmt.close();
				
			}
			if(conn !=null) {
				conn.close();
			}
		}
		
		return list;
	}
	
	public Boolean updateQuantityInStock(Integer item_id, Integer quantity_in_Stock) {
		Boolean isUpdated = false, updateCount = false;
		Connection conn= null;
		PreparedStatement stmt = null;
		
		try {
			conn = OracleConnection.getConnection();
			stmt = conn.prepareStatement(OracleQueries.UPDATEQUANTITYINSTOCK);
			stmt.setInt(1, quantity_in_Stock);
			stmt.setInt(2, item_id);
			
			//Checking return value of update count
			//If executeUpdate returns value greater than 0 updateCount becomes true
			updateCount = (stmt.executeUpdate() > 0);
			
			if(updateCount == true) {
				isUpdated = true;
			}
		}catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isUpdated;
	}
}
