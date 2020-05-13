package routePlanningService.overview;

public class Flight {

	private String myFlightnumber;
	private String myStart;
	private double myLongitude;
	private double myLatitude;
	private String myTarget;

	// ctor
	public Flight(String flightnumber, String start, String target) {
		myFlightnumber = flightnumber;
		myStart = start;
		myTarget = target;
	}

	// ctor
	public Flight(String flightnumber, String start, double longitude, double latitude, String target) {
		this(flightnumber, start, target);
		myLongitude = longitude;
		myLatitude = latitude;
	}

	public String getFlightnumber() {
		return myFlightnumber;
	}

	public String getStart() {
		return myStart;
	}

	public String getTarget() {
		return myTarget;
	}

	public double getLongitude() {
		return myLongitude;
	}

	public double getLatitude() {
		return myLatitude;
	}
}
