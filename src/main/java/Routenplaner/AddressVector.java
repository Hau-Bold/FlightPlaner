package Routenplaner;

import java.util.Vector;

@SuppressWarnings("serial")
public class AddressVector extends Vector<String> {

	/**
	 * to create a Adress
	 * 
	 * @param counter
	 *            - the counter
	 * @param street
	 *            - the street
	 * @param city
	 *            - the city
	 * @param country
	 *            - the country
	 */
	public AddressVector(String counter, String street, String city, String country,String longitude,String latitude) {
		this.add(counter);
		this.add(street);
		this.add(city);
		this.add(country);
		this.add(longitude);
		this.add(latitude);
	}

}
