package GroceryStore;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GroceryTotal {
	BigDecimal gross = new BigDecimal(0);
	BigDecimal vat = new BigDecimal(0);
	
	public BigDecimal getGross() {
		return gross;
	}
	public void setGross(double grossValue) {
		
		
		BigDecimal bd = new BigDecimal(grossValue);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		this.gross = bd;
	}
	
	public BigDecimal getVat() {	
		return vat;
	}
	
	
	public void setVat(BigDecimal bd) {	
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		this.vat = bd;
	}
	public void incrementTotal(BigDecimal unit_price) {
		this.gross = this.gross.add(unit_price);
	}

}
