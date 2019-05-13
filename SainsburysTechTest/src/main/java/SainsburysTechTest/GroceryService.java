package SainsburysTechTest;

import java.io.IOException;
import java.net.URL;

import okhttp3.*;

public class GroceryService {

	public String getGrocerciesAsHTML(URL url) throws IOException {
		
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		
		
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}
}
