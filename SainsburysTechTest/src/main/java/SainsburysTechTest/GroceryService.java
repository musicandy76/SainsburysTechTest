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
	
	private static  final String BASE_URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb";
	
	private URL groceriesURL;
	
	private OkHttpClient client = new OkHttpClient();
	
	
	public GroceryService() {
		
	}
	
	public GroceryService(String url) throws MalformedURLException { 
		groceriesURL= new URL(url);
	}

	public String getGrocerciesAsHTML() throws IOException {	
		return buildAndExecuteRequest(groceriesURL).body().string();
	}
	
	public Response buildAndExecuteRequest(URL url) throws IOException
	{
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		
		return client.newCall(request).execute();
		 
		
	}

	public GroceryList scrapeGrociesListfromHTML() throws IOException {
   			
	  GroceryList groceryList = new GroceryList();
	  Document doc = Jsoup.parse(getGrocerciesAsHTML());
	  
	  Element link = doc.select("a").first();
	  
	  // Read all elements and add a root "results" to the JSON object
	  Elements productList = doc.select(".gridView .gridItem");
	  
	  for (Element element : productList) { 
		  groceryList.addItem(getGroceryItemFromElementLink(element));
	  }
	  
	  return groceryList;
	}

	private GroceryItem getGroceryItemFromElementLink(Element element) throws IOException {
		GroceryItem groceryItem = new GroceryItem();
		Element productLink = element.select("a").first();
		
		Document doc = Jsoup.parse(getProductSummaryFromHTML(productLink));
		System.out.println(doc.toString());
		   
	    return groceryItem;
	}

	private String getProductSummaryFromHTML(Element productLink) throws MalformedURLException, IOException {
		String htmlProductSummary = "";
		String url;
		 
		String linkString = productLink.attr("abs:href"); // ftr, neither this nor absUrl("href") works
		if (linkString.isEmpty()) { // check if returned "" (i.e., the problem at hand)
		 	url = (BASE_URL +productLink.attr("href")); // concatenate baseURI to relative ref
		}
	     else { // for all the properly returned absolute refs
	    	url = productLink.attr("abs:href");
	    }
	    
	    if(!url.isEmpty()) {
	    	URL urlRequest = new URL(url);
	    	htmlProductSummary=  buildAndExecuteRequest(urlRequest).body().string();
	    }
	    
	    return htmlProductSummary;
	}

	public String convertGroceryListToJSON(GroceryList groceryList) {
				
		final GsonBuilder builder = new GsonBuilder();
		    builder.disableHtmlEscaping();
		    
		final Gson gson = builder.create();	
		return gson.toJson(groceryList, GroceryList.class);
	}
}
