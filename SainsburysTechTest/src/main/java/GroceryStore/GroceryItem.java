package GroceryStore;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GroceryItem {
	private String title;
	private Integer kcal_per_100g;
	private BigDecimal unit_price = new BigDecimal(0);
	private String description;
	
	public GroceryItem(String title, Integer kcal_per_100g, double unit_price, String description) {
		this.title = title;
		this.kcal_per_100g = kcal_per_100g;
		
		BigDecimal bd = new BigDecimal(unit_price);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		this.unit_price = bd;
		this.description = description;
		
	}
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

	

	public static class GroceryItemBuilder {
		
		private String title;
		private Integer kcal_per_100g;
		private double unit_price;
		private String description;
		
		public GroceryItemBuilder() {}
		
		public GroceryItemBuilder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		public GroceryItemBuilder setKcal_per_100g(Integer kcal_per_100g ) {
			this.kcal_per_100g = kcal_per_100g;
			return this;
		}
		
		public GroceryItemBuilder  setUnitPrice(double unit_Price)  {
			this.unit_price = unit_Price;
			return this;
		}
		
		public GroceryItemBuilder  setDescription(String  description)  {
			this.description = description;
			return this;
		}
		
		public GroceryItem build() {
			return new GroceryItem(title, kcal_per_100g, unit_price , description);
		}
	}
}



