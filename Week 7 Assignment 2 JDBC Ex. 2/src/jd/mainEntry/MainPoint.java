package jd.mainEntry;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import jd.dao.ItemDAO;
import jd.dao.OrderDAO;
import jd.model.Item;

public class MainPoint {
	
	public static void menu() {
		System.out.println("Cart Order System");
		System.out.println("-------------------------");
		System.out.println("1. Display items in stock");
		System.out.println("2. Add to cart");
		System.out.println("3. Checkout");
		System.out.println("4. Quit");
		System.out.print("> ");
	}
	
	public static void displayItemsInStock() throws SQLException {
		ItemDAO i = new ItemDAO();
		List<Item> itemsInStock = i.getItemsInStock();
		System.out.println("");
		System.out.printf("%-10s | %-30s | %-16s | %-15s", "Item ID", "Item Name", "Items In Stock", "Item Price");
		System.out.println("");
		System.out.println("---------------------------------------------------------------------------");
		
		for(Item item : itemsInStock) {
			System.out.printf("%-10s | %-30s | %-16s | %-15s", item.getItem_id(), item.getItem_name(), item.getQuantity_in_stock(), item.getItem_price());
			System.out.println("");
		}
		
		System.out.println("");
	}
	
	public static void displayCart(Map<Integer, Integer> cartItems) throws SQLException {
		ItemDAO i = new ItemDAO();
		Integer totalQty = 0, countRows = 0;
		Double total = 0D;
		DecimalFormat df = new DecimalFormat("#.00");
		
		System.out.println("");
		System.out.printf("%-10s | %-30s | %-16s | %-15s | %-15s", "Item ID", "Item Name", "Quantity", "Item Price", "Sub Total");
		System.out.println("");
		System.out.println("---------------------------------------------------------------------------------------------------");
		
		for(Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
			Float subTotal = 0F;
			totalQty += entry.getValue();
			for(Item item : i.getItemsInStock()) {
				if(item.getItem_id().equals(entry.getKey())) {
					subTotal = item.getItem_price() * entry.getValue();
					System.out.printf("%-10s | %-30s | %-16s | %-15s | %-15s", item.getItem_id(), item.getItem_name(), entry.getValue(), df.format(item.getItem_price()), df.format(subTotal));
					System.out.println("");
					total += (item.getItem_price() * entry.getValue());
				}
			}
			countRows++;
		}
		
		System.out.println("");
		System.out.println("Total Items (" + countRows + ") | Total Quantity (" + totalQty +") | Total $" + df.format(total));
		System.out.println("");
	}

	public static void main(String[] args) throws SQLException {
		Scanner read = new Scanner(System.in);
		int choice = 0;
		
		OrderDAO oDAO = new OrderDAO();
		
		while(choice != 4) {
			menu();
			choice = read.nextInt();
			read.nextLine();
			switch(choice) {
				case 1:
					displayItemsInStock();
					break;
				case 2:
					displayItemsInStock();
					System.out.print("Please choose an item using the Item ID: ");
					int item = read.nextInt();
					System.out.print("Quantity? ");
					int quantity = read.nextInt();
					
					if(oDAO.addToCart(item, quantity) == true) {
						System.out.println("Item added to cart successfully!\n");
					}else {
						System.out.println("Item could not be added to cart, item in stock is less than quantity added to cart!\n");
					}
					
					break;
				case 3:
					Map<Integer, Integer> cartItems = oDAO.getCart();
					if(oDAO.createOrder(cartItems)) {
						System.out.println("");
						System.out.println("Order was created succesfully!");
						displayCart(cartItems);
					}
					
					break;
				case 4:
					System.out.println("ok");
					break;
				default:
					System.out.println("Invalid input\n Please enter a number between 1 - 4");	
			}
		}
		read.close();
	}

}
