package view.Impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import routePlanning.Constants.Constants;
import routePlanning.Impl.GPSCoordinate;
import view.Contract.IHtmlExecutor;

public class HtmlExecutor implements IHtmlExecutor {

	// SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss
	// z");
	// Date date = new Date(System.currentTimeMillis());
	// System.out.println(formatter.format(date));
	//

	private String myDirectory;
	private String myTitle;
	private String myHeader;
	private LocalDate localDate;
	private int dayOfToday;
	private String monthOfToday;
	private int year;
	private BufferedWriter myBufferedWriter;

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", Locale.UK);
	private String dayOfWeekInGerman;

	// ctor.
	public HtmlExecutor(String directory, String title, String header, Date today) {

		/** process today date: */
		localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		this.dayOfToday = localDate.getDayOfMonth();
		this.year = localDate.getYear();
		this.monthOfToday = simpleDateFormat.format(today.getTime());
		this.dayOfWeekInGerman = localDate.format(DateTimeFormatter.ofPattern("EEEE", Locale.UK));

		myDirectory = directory;
		myTitle = title;
		myHeader = header;
	}

	private void writeHeadStart() throws IOException {
		writeLineAppendingNewLine("<!doctype html>");
		writeLineAppendingNewLine("<html>");
		writeLineAppendingNewLine("<head>");
		myBufferedWriter.write("<link href=\"");

		myBufferedWriter.write(myDirectory + File.separator + Constants.ASSETS + File.separator + Constants.FONTS
				+ File.separator + Constants.ROBOTOFONT + "\"");

		writeLineAppendingNewLine(" rel='stylesheet' type='text/css'>");
		writeLineAppendingNewLine("<meta charset=\"UTF-8\">");
		writeLineAppendingNewLine("<title>" + myTitle + "</title>");
	}

	private void writeHeadEnd() {
		writeLineAppendingNewLine("</head>");
	}

	private void writeBodyStart(int dayOfToday, String monthOfToday, int year) {

		writeLineAppendingNewLine("<body id=\"body\">");
		// myBufferedWriter.write("<img src=\"");
		writeLineAppendingNewLine("<h1>");
		writeLineAppendingNewLine(Constants.FLIGHTPLANER);
		// myBufferedWriter.write(" class=\"Intersportlogo\" alt =
		// \"Intersport_Logo_blank\" align=\"left\"/>");
		writeLineAppendingNewLine("</h1>");
		writeLineAppendingNewLine("</br>");
		writeLineAppendingNewLine("</br>");
		writeLineAppendingNewLine("<h1>");
		writeLineAppendingNewLine(String.format(myHeader, dayOfWeekInGerman, dayOfToday, monthOfToday, year));
		writeLineAppendingNewLine("</h1>");
		writeLineAppendingNewLine("</br>");
		writeLineAppendingNewLine("</br>");
	}

	private void writeBodyEnd() {
		writeLineAppendingNewLine("</body>");
		writeLineAppendingNewLine("</html>");
	}

	private void writeStyle() {

		// from top to bottom:
		writeLineAppendingNewLine("<style>");

		/** html */
		writeLineAppendingNewLine("html");
		writeLineAppendingNewLine("{");
		writeLineAppendingNewLine("height: 100%;");
		writeLineAppendingNewLine("}");
		writeLine();

		/** body */
		writeLineAppendingNewLine("body");
		writeLineAppendingNewLine("{");
		writeLineAppendingNewLine("min-height: 100%;");
		writeLineAppendingNewLine("}");
		writeLine();

		/** h1 */
		writeLineAppendingNewLine("h1 {");
		writeLineAppendingNewLine("color: #001F4F;");
		writeLineAppendingNewLine("clear: right;");
		writeLineAppendingNewLine("}");
		writeLine();

		/** Logo */
		// bufferedWriter.write(".Intersportlogo {");
		// bufferedWriter.newLine();
		// bufferedWriter.write("float:left;");
		// bufferedWriter.newLine();
		// bufferedWriter.write("width: 15%;");
		// bufferedWriter.newLine();
		// bufferedWriter.write("display: block;");
		// bufferedWriter.newLine();
		// bufferedWriter.write("}");
		// bufferedWriter.newLine();
		// bufferedWriter.newLine();

		/** body - start */
		writeLineAppendingNewLine("#body {");
		writeLineAppendingNewLine("background-size: 100% 100%;");
		writeLineAppendingNewLine("text-align: center;");
		writeLineAppendingNewLine(
				"background-image: url(\" " + Constants.IMAGE + Constants.Slash + Constants.BackgroundImage + "\");");
		writeLineAppendingNewLine("font-family: Roboto, sans-serif;");
		writeLineAppendingNewLine("background-repeat: no-repeat;");
		writeLineAppendingNewLine("font-size: 20px;");
		writeLineAppendingNewLine("margin: 0;");
		writeLineAppendingNewLine("padding: 0;");
		writeLineAppendingNewLine("min-height: 100%");
		writeLineAppendingNewLine("}");
		writeLine(); /** remove */

		// formular
		writeLineAppendingNewLine("#formularId");
		writeLineAppendingNewLine("{");
		writeLineAppendingNewLine("color: " + Constants.WHITE + ";");
		writeLineAppendingNewLine("clear: right;");
		writeLineAppendingNewLine("font-size: 20px;");
		writeLineAppendingNewLine("}");
		writeLine();
		/** the table wrapper */
		// myBufferedWriter.write("#tableWrapperId {");
		// myBufferedWriter.newLine();
		// myBufferedWriter.write("}");
		// myBufferedWriter.newLine();
		// myBufferedWriter.newLine();

		/** the table */
		writeLineAppendingNewLine("#tableId {");
		writeLineAppendingNewLine("text-align: center;");
		writeLineAppendingNewLine("width:80%;");
		writeLineAppendingNewLine("height:80%;");
		writeLineAppendingNewLine("table-spacing: 2px;");
		writeLineAppendingNewLine("}");
		writeLine();

		/** the table head */
		writeLineAppendingNewLine(".tableheader {");
		writeLineAppendingNewLine("}");
		writeLine();

		/** the table rows */
		// bufferedWriter.write(".tableRow {");
		// bufferedWriter.newLine();
		// bufferedWriter.write("line-height: " +
		// Utils.determineRowHeightInPercent(lstAppointmentsOfToday) + ";");
		// bufferedWriter.newLine();
		// bufferedWriter.write("}");
		// bufferedWriter.newLine();
		// bufferedWriter.newLine();

		// even
		writeLineAppendingNewLine("tr:nth-child(even) {");
		writeLineAppendingNewLine("background-color:" + Constants.WHITE + ";");
		writeLineAppendingNewLine("}");
		writeLine();

		// odd
		writeLineAppendingNewLine("tr:nth-child(odd) {");
		writeLineAppendingNewLine("background-color:" + Constants.GRAY + ";");
		writeLineAppendingNewLine("}");
		writeLine();

		/** the table data for appointments */
		writeLineAppendingNewLine(".tableData {");
		writeLineAppendingNewLine("text-align: center;");
		// writeLineAppendingNewLine("background-color: " + Constants.CORAL + ";");
		writeLineAppendingNewLine("width: 35%;");
		writeLineAppendingNewLine("border: 2px solid black;");
		writeLineAppendingNewLine("filter:alpha(opacity=90);");
		writeLineAppendingNewLine("-moz-opacity: 0.90;");
		writeLineAppendingNewLine("opacity: 0.90;");
		writeLineAppendingNewLine("}");
		writeLine();

		/** the table data for appointments that should be colored */
		// myBufferedWriter.write(".appointmentNotEmpty {");
		// myBufferedWriter.newLine();
		// myBufferedWriter.write("background-color: " + Constants.CORAL + ";");
		// myBufferedWriter.newLine();
		// myBufferedWriter.write("text-align: left;");
		// myBufferedWriter.newLine();
		// myBufferedWriter.write("padding: 6px;");
		// myBufferedWriter.newLine();
		// myBufferedWriter.write("}");
		// myBufferedWriter.newLine();
		// myBufferedWriter.newLine();

		writeLineAppendingNewLine("</style>");
	}

	public void writeTable(ArrayList<GPSCoordinate> coordinates) {

		writeLineAppendingNewLine("<div id =\"tableWrapperId\" align=\"center\">");
		writeLineAppendingNewLine("<table id=\"tableId\">");
		writeLineAppendingNewLine("<thead class=\"tableheader\">");
		writeLineAppendingNewLine("</thead>");
		writeLineAppendingNewLine("<tbody>");

		coordinates.forEach(gps -> writeTableRow(gps));

		writeLineAppendingNewLine("</tbody>");
		writeLineAppendingNewLine("</table>");
		writeLineAppendingNewLine("</div>");
	}

	private void writeTableRow(GPSCoordinate gps) {

		writeLineAppendingNewLine("<tr class=\"tableRow\">");
		writeLineAppendingNewLine("<td class=\"tableData\">" + gps.getStreet() + "</td>");
		writeLineAppendingNewLine("<td class=\"tableData\">" + gps.getCity() + "</td>");
		writeLineAppendingNewLine("<td class=\"tableData\">" + gps.getCountry() + "</td>");
		writeLineAppendingNewLine("<td class=\"tableData\">" + gps.getLongitude() + "</td>");
		writeLineAppendingNewLine("<td class=\"tableData\">" + gps.getLatitude() + "</td>");
		writeLineAppendingNewLine("</tr>");
	}

	@Override
	public void write(ArrayList<GPSCoordinate> master) throws IOException {

		File file = new File(
				myDirectory + File.separator + Constants.ASSETS + File.separator + Constants.FlightPlanerHtml);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}

		myBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

		writeHeadStart();
		writeStyle();
		writeHeadEnd();
		writeBodyStart(dayOfToday, monthOfToday, year);
		writeTable(master);
		writeBodyEnd();

		myBufferedWriter.close();
	}

	private void writeLineAppendingNewLine(String line) {
		writeLine(line);
		writeLine();
	}

	private void writeLine(String line) {
		try {
			myBufferedWriter.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeLine() {
		try {
			myBufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
