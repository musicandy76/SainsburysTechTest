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
		total.addToGrossTotal(item1.getUnit_price());
		total.calculateVAT();
	}

	public BigDecimal getGrossTotal() {
		return total.getGross();
	}

	public void setGrossTotal(double gross) {
		total.setGross(roundCurrency(gross));
	}
	
	double roundCurrency(double value) {
		BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	


}
