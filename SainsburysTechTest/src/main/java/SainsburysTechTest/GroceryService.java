package SainsburysTechTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import GroceryStore.GroceryItem;
import GroceryStore.GroceryList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GroceryService {
	
	private URL groceriesURL;
	
	
	public GroceryService() {
		
	}
	
	public GroceryService(String url) throws MalformedURLException { 
		groceriesURL= new URL(url);
	}

	public String getGrocerciesAsHTML() throws IOException {
		
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
				.url(groceriesURL)
				.get()
				.build();
		
		
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}

	public GroceryList scrapeGrociesListfromHTML() throws IOException {
   			
	  GroceryList groceryList = new GroceryList();
	  Document doc = Jsoup.parse(getGrocerciesAsHTML());
	  
	  // Read all elements and add a root "results" to the JSON object
	  Elements productList = doc.select(".gridView .gridItem");
	  
	  for (Element element : productList)
	  { 
		 GroceryItem groceryItem = new GroceryItem();
		  
		 Node elementNew =  element.select(".product .productInfo .productNameAndPromotions").first().childNode(1);
		 Node productElement = elementNew.childNode(1);
		 TextNode node = (TextNode) productElement.childNode(0);
		 groceryItem.setTitle(node.text());
		 
		 
		 groceryList.addItem(groceryItem);
		 System.out.println(node.toString());
	  }
	
	  
	  return groceryList;
	}

	public String convertGroceryListToJSON(GroceryList groceryList) {
				
		final GsonBuilder builder = new GsonBuilder();
		    builder.disableHtmlEscaping();
		    
		final Gson gson = builder.create();	
		return gson.toJson(groceryList, GroceryList.class);
	}
}
