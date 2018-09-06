package weather;

import org.jdom.Attribute;

public class Cloud
{
	private String value;
	@Override
	public String toString() {
		return "Cloud [value=" + value + ", name=" + name + "]";
	}

	private String name;
	
	public Cloud(String value, String name) {
		this.value=value;
		this.name=name;
	}
}
