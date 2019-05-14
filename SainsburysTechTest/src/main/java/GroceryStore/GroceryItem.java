package GroceryStore;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GroceryItem {
	private String title;
	private Integer kcal_per_100g;
	private BigDecimal unit_price = new BigDecimal(0);
	private String description;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getKcal_per_100g() {
		return kcal_per_100g;
	}
	public void setKcal_per_100g(Integer kcal_per_100g) {
		this.kcal_per_100g = kcal_per_100g;
	}
	public BigDecimal getUnit_price() {
		return unit_price;
	}
	
	public void setUnit_price(double unit_priceValue) {
		
		BigDecimal bd = new BigDecimal(unit_priceValue);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		this.unit_price= bd;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}



}
