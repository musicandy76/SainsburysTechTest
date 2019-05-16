package GroceryStore;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class GroceryList {

	List<GroceryItem> results = new ArrayList<GroceryItem>();
	
	GroceryTotal total = new GroceryTotal();

	public void addItem(GroceryItem item1) {
		results.add(item1);
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

	public void addToGrossTotal(BigDecimal unit_price) {
		total.incrementTotal(unit_price);
	}

	public void calculateVAT() {
		BigDecimal vat = total.getGross().divide(new BigDecimal(1.20),MathContext.DECIMAL128).subtract(total.getGross()).multiply(new BigDecimal(-1));
		total.setVat(vat);
		
	}


}
