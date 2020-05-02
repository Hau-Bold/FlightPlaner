package gps_coordinates;

public class GpsCoordinate {

	private int myId;
	private double myLatitude;
	private double myLongitude;
	private String myStreet;
	private String myCity;
	private String myCountry;

	// ctor
	public GpsCoordinate(double latitude, double longitude) {
		myLatitude = latitude;
		myLongitude = longitude;
	}

	// ctor
	public GpsCoordinate(int id, String street, String city, String country, double longitude, double latitude) {
		this(latitude, longitude);
		myId = id;
		myStreet = street;
		myCity = city;
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
		this.myStreet = street;
	}

	public String getCity() {
		return myCity;
	}

	public void setCity(String city) {
		myCity = city;
	}

	public String getCountry() {
		return myCountry;
	}

	public void setCountry(String country) {
		this.myCountry = country;
	}

	public double getLatitude() {
		return myLatitude;
	}

	public double getLongitude() {
		return myLongitude;
	}

	@Override
	public String toString() {
		return "GpsCoordinate [id=" + myId + ", myLatitude=" + myLatitude + ", myLongitude=" + myLongitude
				+ ", myStreet=" + myStreet + ", myCity=" + myCity + ", myCountry=" + myCountry + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GpsCoordinate) {
			if (((GpsCoordinate) obj).getLongitude() == this.getLongitude()
					&& ((GpsCoordinate) obj).getLatitude() == this.getLatitude()) {
				return true;
			}
		}
		return false;
	}
}
