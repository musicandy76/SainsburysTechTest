package SainsburysTechTest;

import java.io.IOException;
import java.net.MalformedURLException;

import GroceryStore.GroceryList;

public class SainsburysTechTest {

	public static void main(String[] args) throws IOException {
	
		GroceryService service = new GroceryService(
				"https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html");
		GroceryList response = service.scrapeGrociesListfromHTML();
		String json = service.convertGroceryListToJSON(response);
		System.out.println(json);		

	}

}
