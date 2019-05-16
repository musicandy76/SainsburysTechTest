package GroceryStore;

import java.math.BigDecimal;
import java.math.MathContext;
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
	public void incrementTotal(BigDecimal totalToIncr) {
		this.gross = this.gross.add(totalToIncr);
	}
	
	public void addToGrossTotal(BigDecimal unit_price) {
		incrementTotal(unit_price);
	}

	public void calculateVAT() {
		this.setVat(getGross().divide(new BigDecimal(1.20),MathContext.DECIMAL128).subtract(getGross()).multiply(new BigDecimal(-1)));
	}

}
