package jd.utils;

public class OracleQueries {

	public final static String CREATEORDER = "INSERT INTO ORDER_TABLE "
			+ "(item_id, quantity) "
			+ "values (?,?)";	
	
	public final static String UPDATEQUANTITYINSTOCK = "UPDATE ITEM SET "
			+ "QUANTITY_IN_STOCK = ?"
			+ "WHERE ID = ?";
	
	public final static String GETALLITEMSINSTOCK = "SELECT * FROM ITEM "
			+ "WHERE quantity_in_stock > 0";
}
