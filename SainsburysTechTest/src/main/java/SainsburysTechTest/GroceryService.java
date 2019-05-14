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

	public GroceryList scrapeGrociesListfromHTML() throws IOException {
   			
	  GroceryList groceryList = new GroceryList();
	  Document doc = Jsoup.parse(getGrocerciesAsHTML());
	  
	  // Read all elements and add a root "results" to the JSON object
	  Elements productList = doc.select(".gridView .gridItem");
	
	  return groceryList;
	}

	public String convertGroceryListToJSON(GroceryList groceryList) {
				
		final GsonBuilder builder = new GsonBuilder();
		    builder.disableHtmlEscaping();
		    
		final Gson gson = builder.create();	
		return gson.toJson(groceryList, GroceryList.class);
	}
}
