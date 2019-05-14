package GroceryStore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class GroceryList {

	List<GroceryItem> results = new ArrayList<GroceryItem>();
	
	GroceryTotal total = new GroceryTotal();

	public void addItem(GroceryItem item1) {
		results.add(item1);
	}

	public void setGrossTotal(double gross) {
		total.setGross(roundCurrency(gross));
	}

	public void setGrossVat(double vat) {
		total.setVat(roundCurrency(vat));
	}
	
	double roundCurrency(double value) {
		BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}


}
