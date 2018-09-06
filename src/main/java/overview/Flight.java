package overview;

/*to represent a flight*/
public class Flight {
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatidtude(double latidtude) {
		this.latitude = latidtude;
	}

	private String flightnumber;
	private String start;
	private double longitude;
	private double latitude;
	private String target;
	
	public String getFlightnumber() {
		return flightnumber;
	}

	public String getStart() {
		return start;
	}

	public String getTarget() {
		return target;
	}
	
	/**
	 * Constructor.
	 * @param flightnumber - the flightnumber
	 * @param start - the start
	 * @param target - the target
	 */
	public Flight(String flightnumber, String start, String target) {
		super();
		this.flightnumber = flightnumber;
		this.start = start;
		this.target = target;
	}
	
	/**
	 * Constructor.
	 * @param flightnumber - the flightnumber
	 * @param start - the start
	 */
	public Flight(String flightnumber, String start) {
		super();
		this.flightnumber = flightnumber;
		this.start = start;
		this.target = "";
	}

	/**
	 * Constructor.
	 * @param flightnumber - the flightnumber
	 * @param target 
	 * @param latitude 
	 * @param longitude 
	 * @param start 
	 */
	public Flight(String flightnumber, String start, double longitude, double latitude, String target) {
		super();
		this.flightnumber = flightnumber;
		this.start = start;
		this.longitude=longitude;
		this.latitude=latitude;
		this.target = target;
	}
	
}
