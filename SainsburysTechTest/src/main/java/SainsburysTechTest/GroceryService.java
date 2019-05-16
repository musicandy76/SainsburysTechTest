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
	  
	  try {
		  Document doc = Jsoup.parse(getGrocerciesAsHTML());
		  		  
		  for (Element element : doc.select(".gridView .gridItem")) {
			  groceryList.addItem(getGroceryItemFromElementLink(element));
		  }
  
	  }
	  catch (MalformedURLException e) 
	  {
		  throw e;
	  }

	  return groceryList;
	}

	private GroceryItem getGroceryItemFromElementLink(Element element) throws MalformedURLException, IOException {
		
		try {
			Document doc = Jsoup.parse(getProductSummaryFromHTML(element.select("a").first()));
			return getProductSummary( doc);
		}
		catch (MalformedURLException e)
		{
			throw e;
		}
	}

	private String getProductSummaryFromHTML(Element productLink) throws MalformedURLException, IOException {
		
    	return buildAndExecuteRequest(new URL(
    			productLink.attr("abs:href").isEmpty() ?
    					(BASE_URL +productLink.attr("href")) 
    					: productLink.attr("abs:href"))).body().string();
	    
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

	public GroceryItem  getProductSummary( Document doc) {
		
		GroceryItem groceryItem = new GroceryItem.GroceryItemBuilder()
				.setTitle(getTextNodeText(doc.select(".productTitleDescriptionContainer").select("h1")))
				.setUnitPrice(Double.parseDouble(getTextNodeText(doc.select(".pricePerUnit")).replace("£", "")))
				.build();
		
		getProductInformation(groceryItem, doc);
		return groceryItem; 
	}
	
	public void getProductInformation(GroceryItem item, Document doc)
	{
		item.setDescription(getTextNodeText(doc.select(".productText").select("p")));
		if(doc.select(".nutritionLevel1") != null) {
			
			String result = getTextNodeText(doc.select(".nutritionLevel1")).replace("kcal", "");
			if(result != null && !result.equals("")) {
				item.setKcal_per_100g(Integer.parseInt(result));
			}			
		}
	}

	public String convertGroceryListToJSON(GroceryList groceryList) {
				
		final GsonBuilder builder = new GsonBuilder().disableHtmlEscaping();
		    builder.disableHtmlEscaping();
			
		return new GsonBuilder().disableHtmlEscaping().create().toJson(groceryList, GroceryList.class);
	}
}
