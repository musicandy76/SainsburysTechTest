/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package SainsburysTechTest;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SainsburysTechTest {
    @Test public void testGetGroceryProductsAsHTML() throws IOException {
    	GroceryService service = new GroceryService();
    	
    	String response = service.getGrocerciesAsHTML(new URL("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html"));
    	System.out.println(response);
        assertNotEquals(response, "");
    }
}
