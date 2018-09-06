package gps_coordinates;

/**
 * An object created to save GPS-Coordinates
 * 
 *
 */
public class GpsCoordinate {
	// Parameter
	private int id;
	private double latitude = .0;
	private double longitude = .0;
	private String street = null;
	private String city = null;
	private String country = null;;

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "GpsCoordinate [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", street=" + street
				+ ", city=" + city + ", country=" + country + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Constructor.
	 * 
	 * @param latitude
	 *            - the latitude
	 * @param longitude
	 *            - the longitude
	 */
	public GpsCoordinate(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	
	/**
	 * Constructor.
	 * @param id - the id
	 * @param street - the latitude
	 * @param city - the longitude
	 * @param street - the street
	 * @param longitude - the city
	 * @param latitude - the country
	 */
	public GpsCoordinate(int id, String street, String city, String country, double longitude, double latitude) {
		this.id = id;
		this.street = street;
		this.city = city;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Constructor.
	 * @param id - the id
	 * @param street - the street
	 * @param city - the city
	 * @param country - the country
	 */
	public GpsCoordinate(int id, String street, String city, String country) {
		this.id = id;
		this.street = street;
		this.city = city;
		this.country = country;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GpsCoordinate) {
			if (((GpsCoordinate) obj).getLongitude() == this.getLongitude()
					&& ((GpsCoordinate) obj).getLatitude() == this.getLatitude()
					) {
				return true;
			}
		}
		return false;
	}
}
