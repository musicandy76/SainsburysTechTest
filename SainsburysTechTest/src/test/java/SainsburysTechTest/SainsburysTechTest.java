/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package SainsburysTechTest;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

import GroceryStore.GroceryItem;
import GroceryStore.GroceryList;

public class SainsburysTechTest {

	private GroceryService service;

	@Before
	public void setUp() throws MalformedURLException {
		service = new GroceryService(
				"https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html");
	}

	@Test
	public void testGetGroceryProductsAsHTML() throws IOException {

		GroceryList response = service.scrapeGrociesListfromHTML();
		
		String json = service.convertGroceryListToJSON(response);
		System.out.println(json);
		
		assertNotEquals(json, null);
	}


	@Test 
    public void testExampleJSONParsesasPOJO() {
		
		// TODO : Refactor to use a Grocery Item builder patter
		
		GroceryList groceryList = new GroceryList();
		
		GroceryItem item1 = new GroceryItem();
		item1.setTitle("Sainsbury's Strawberries 400g");
		item1.setKcal_per_100g(33);
		item1.setUnit_price(1.75);
		item1.setDescription("by Sainsbury's strawberries");
		
		GroceryItem item2 = new GroceryItem();
		item2.setTitle("Sainsbury's Blueberries 200g");
		item2.setUnit_price(1.75);
		item2.setKcal_per_100g(45);
		item2.setDescription("by Sainsbury's blueberries");
		
		GroceryItem item3 = new GroceryItem();
		item3.setTitle("Sainsbury's Cherry Punnet 200g");
		item3.setKcal_per_100g(52);
		item3.setUnit_price(1.5);
		item3.setDescription("Cherries");
		
		
		groceryList.addItem(item1);
		groceryList.addItem(item2);
		groceryList.addItem(item3);
		groceryList.setGrossTotal(5.0);
		groceryList.setGrossVat(0.83);
		
		
    	String compareString = "{\"results\":[{\"title\":\"Sainsbury's Strawberries 400g\",\"kcal_per_100g\":33,\"unit_price\":1.75,\"description\":\"by Sainsbury's strawberries\"},{\"title\":\"Sainsbury's Blueberries 200g\",\"kcal_per_100g\":45,\"unit_price\":1.75,\"description\":\"by Sainsbury's blueberries\"},{\"title\":\"Sainsbury's Cherry Punnet 200g\",\"kcal_per_100g\":52,\"unit_price\":1.50,\"description\":\"Cherries\"}],\"total\":{\"gross\":5.00,\"vat\":0.83}}";
    			
    	
    	String result = service.convertGroceryListToJSON(groceryList);
    	System.out.println(result);
    	System.out.println(compareString);
    	
    	
    	assertTrue(result.equals(compareString));
	}
    			
}
