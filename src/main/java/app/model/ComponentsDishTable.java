package app.model;

public class ComponentsDishTable {

	public String groupComponent;
	public String nameComponent;
	public String amount;
	public String unit;
	public String recomendedAmount;

	public ComponentsDishTable(String groupComponent, String nameComponent, String amount, String unit) {
		this.groupComponent = groupComponent;
		this.nameComponent = nameComponent;
		this.amount = amount;
		this.unit = unit;
	}
	
	public ComponentsDishTable(String groupComponent, String nameComponent, String amount, String unit, String recomendedAmount) {
		this.groupComponent = groupComponent;
		this.nameComponent = nameComponent;
		this.amount = amount;
		this.unit = unit;
		this.recomendedAmount = recomendedAmount;
	}

	public ComponentsDishTable() {

	}


	public String getRecomendedAmount() {
		return recomendedAmount;
	}

	public void setRecomendedAmount(String recomendedAmount) {
		this.recomendedAmount = recomendedAmount;
	}

	public String getGroupComponent() {
		return groupComponent;
	}

	public void setGroupComponent(String groupComponent) {
		this.groupComponent = groupComponent;
	}

	public String getNameComponent() {
		return nameComponent;
	}

	public void setNameComponent(String nameComponent) {
		this.nameComponent = nameComponent;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
