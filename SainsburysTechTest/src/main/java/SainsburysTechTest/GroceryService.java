package SainsburysTechTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import GroceryStore.GroceryList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.*;



public class GroceryService {
	
	private URL groceriesURL;
	
	
	public GroceryService() {
		
		
		
	}
	
	private static class HtmlContainer {

        @SerializedName("html")
        private String mHtml;

        public String getHtml() {
            return mHtml;
        }
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

	public String getGroceriesSummaryAsJSON() throws IOException {
   			
	  Document doc = Jsoup.parse(getGrocerciesAsHTML());
	  
	  // Read all elements and add a root "results" to the JSON object
	  Elements productList = doc.select(".gridView .gridItem");
	  
	  //Go through each of the items in the Elemets list and request the item data through an HTTP GET for each item
	  
	  // Format this data into suitable JSON format we need
	  
	  
	  return "";
			
	
	}

	public String convertGroceryListToJSON(GroceryList groceryList) {
		
		String groceryListTemp = "{\n" + 
    			"  \"results\": [\n" + 
    			"    {\n" + 
    			"      \"title\": \"Sainsbury's Strawberries 400g\",\n" + 
    			"      \"kcal_per_100g\": 33,\n" + 
    			"      \"unit_price\": 1.75,\n" + 
    			"      \"description\": \"by Sainsbury's strawberries\"\n" + 
    			"    },\n" + 
    			"    {\n" + 
    			"      \"title\": \"Sainsbury's Blueberries 200g\",\n" + 
    			"      \"kcal_per_100g\": 45,\n" + 
    			"      \"unit_price\": 1.75,\n" + 
    			"      \"description\": \"by Sainsbury's blueberries\"\n" + 
    			"    },\n" + 
    			"    {\n" + 
    			"      \"title\": \"Sainsbury's Cherry Punnet 200g\",\n" + 
    			"      \"kcal_per_100g\": 52,\n" + 
    			"      \"unit_price\": 1.5,\n" + 
    			"      \"description\": \"Cherries\"\n" + 
    			"    }\n" + 
    			"\n" + 
    			"  ],\n" + 
    			"  \"total\": {\n" + 
    			"    \"gross\": 5.00,\n" + 
    			"    \"vat\": 0.83\n" + 
    			"  }\n" + 
    			"}";

		
		 final GsonBuilder builder = new GsonBuilder();
		    builder.disableHtmlEscaping();
		    
		final Gson gson = builder.create();
		
		return gson.toJson(groceryList, GroceryList.class);
		
	}
}
