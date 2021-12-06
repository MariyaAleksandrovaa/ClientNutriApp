package app.objects;

public class LabelObj {
	
	public String component;
	public String amount;
	public String recomendedAmount;
	
	public LabelObj(String component, String amount, String recomendedAmount) {
		this.component= component;
		this.amount = amount;
		this.recomendedAmount = recomendedAmount;
	}
	
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRecomendedAmount() {
		return recomendedAmount;
	}
	public void setRecomendedAmount(String recomendedAmount) {
		this.recomendedAmount = recomendedAmount;
	}
	

}
