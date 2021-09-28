package app.model;

public class ComponentsDishTable {

	public String groupComponent;
	public String nameComponent;
	public Float amount;
	public String unit;

	public ComponentsDishTable(String groupComponent, String nameComponent, Float amount, String unit) {
		this.groupComponent = groupComponent;
		this.nameComponent = nameComponent;
		this.amount = amount;
		this.unit = unit;
	}

	public ComponentsDishTable() {

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

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
