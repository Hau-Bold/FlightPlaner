package routePlanning.Impl;

public class GPSCoordinate {

	private int myId;
	private double myLatitude;
	private double myLongitude;
	private String myStreet;
	private String myCity;
	private String myCountry;
	private String myPostalCode;

	public GPSCoordinate(double latitude, double longitude) {
		myLatitude = latitude;
		myLongitude = longitude;
	}

	public GPSCoordinate(String street, String city, String postalCode, String country, double longitude,
			double latitude) {
		this(latitude, longitude);
		myStreet = street;
		myCity = city;
		myPostalCode = postalCode;
		myCountry = country;
	}

	public int getId() {
		return myId;
	}

	public void setId(int id) {
		myId = id;
	}

	public String getStreet() {
		return myStreet;
	}

	public void setStreet(String street) {
		myStreet = street;
	}

	public String getCity() {
		return myCity;
	}

	public String getPostalCode() {
		return myPostalCode;
	}

	public void setCity(String city) {
		myCity = city;
	}

	public String getCountry() {
		return myCountry;
	}

	public void setCountry(String country) {
		myCountry = country;
	}

	public double getLatitude() {
		return myLatitude;
	}

	public double getLongitude() {
		return myLongitude;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GPSCoordinate) {
			// TODO check xml from openstreetmap and use Double.compare(d1, d2)
			if (((GPSCoordinate) obj).getLongitude() == myLongitude
					&& ((GPSCoordinate) obj).getLatitude() == myLatitude) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "GPSCoordinate{" + "myStreet='" + myStreet + '\'' + ", myCity='" + myCity + '\'' + ", myCountry='"
				+ myCountry + '\'' + ", myLatitude='" + myLatitude + '\'' + ", myLongtitude='" + myLongitude + '\''
				+ '}';
	}
}
