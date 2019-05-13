package SainsburysTechTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.*;

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
}
