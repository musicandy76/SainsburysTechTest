package GroceryStore;

import java.util.ArrayList;
import java.util.List;

public class GroceryList {

	List<GroceryItem> results = new ArrayList<GroceryItem>();
	
	GroceryTotal total = new GroceryTotal();

	public void addItem(GroceryItem item1) {
		results.add(item1);
	}

	public void setGrossTotal(double gross) {
		total.setGross(gross);
	}

	public void setGrossVat(double vat) {
		total.setVat(vat);
	}


}
