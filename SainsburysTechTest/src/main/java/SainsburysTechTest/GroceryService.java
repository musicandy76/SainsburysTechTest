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
		  
		  GroceryItem item = getGroceryItemFromElementLink(element);
		  
		  groceryList.addToGrossTotal(item.getUnit_price());
		  groceryList.addItem(item);
		  
	  }
	  
	  groceryList.calculateVAT();
	  
	  
	  return groceryList;
	}

	private GroceryItem getGroceryItemFromElementLink(Element element) throws IOException {
		GroceryItem groceryItem = new GroceryItem();
		Element productLink = element.select("a").first();
		
		Document doc = Jsoup.parse(getProductSummaryFromHTML(productLink));
		
		getProductSummary(groceryItem, doc);
		getProductInformation(groceryItem, doc);
		
		
		
		   
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

	public void getProductInformation(GroceryItem item, Document doc ) {
		item.setDescription(getTextNodeText(doc.select(".productText").select("p")));
		if(doc.select(".nutritionLevel1") != null) {
			
			String result = getTextNodeText(doc.select(".nutritionLevel1")).replace("kcal", "");
			if(result != null && !result.equals("")) {
				item.setKcal_per_100g(Integer.parseInt(result));
			}			
		}
	}
	
	private String  getTextNodeText(Elements elements)
	{
		for(Element element : elements) 
		{		
			if(element.childNode(0) instanceof TextNode) {
				return ((TextNode)element.childNode(0)).text();
			}
		}
		
		return "";
	}

	public  void getProductSummary(GroceryItem item, Document doc) {
		item.setTitle(getTextNodeText(doc.select(".productTitleDescriptionContainer").select("h1")));
		item.setUnit_price(Double.parseDouble(getTextNodeText(doc.select(".pricePerUnit")).replace("£", "")));
	}

	public String convertGroceryListToJSON(GroceryList groceryList) {
				
		final GsonBuilder builder = new GsonBuilder();
		    builder.disableHtmlEscaping();
		    
		final Gson gson = builder.create();	
		return gson.toJson(groceryList, GroceryList.class);
	}
}
