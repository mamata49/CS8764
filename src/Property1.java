
/*
 * Represents property in the RDF files 
 */
public class Property1 {

	private String name;   
	private String type;
	private boolean isObjectProperty;
	private int maxValue;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isObjectProperty() {
		return isObjectProperty;
	}
	public void setObjectProperty(boolean isObjectProperty) {
		this.isObjectProperty = isObjectProperty;
	}
	public int getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Property [name=" + name + ", type=" + type
				+ ", isObjectProperty=" + isObjectProperty + ", maxValue="
				+ maxValue + "]";
	}
   
}
