package view.Contract;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import routePlanning.Constants.Constants;
import view.Impl.IHtmlExecutor;

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

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", Locale.GERMAN);
	private String dayOfWeekInGerman;

	// ctor.
	public HtmlExecutor(String directory, String title, String header, Date today) {

		/** process today date: */
		localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		this.dayOfToday = localDate.getDayOfMonth();
		this.year = localDate.getYear();
		this.monthOfToday = simpleDateFormat.format(today.getTime());
		this.dayOfWeekInGerman = localDate.format(DateTimeFormatter.ofPattern("EEEE", Locale.GERMAN));

		myDirectory = directory;
		myTitle = title;
		myHeader = header;
	}

	private void writeHeadStart(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("<!doctype html>");
		bufferedWriter.newLine();
		bufferedWriter.write("<html>");
		bufferedWriter.newLine();
		bufferedWriter.write("<head>");
		bufferedWriter.newLine();
		bufferedWriter.write("<link href=\"");

		bufferedWriter.write(myDirectory + File.separator + Constants.ASSETS + File.separator + Constants.FONTS
				+ File.separator + Constants.ROBOTOFONT + "\"");

		bufferedWriter.write(" rel='stylesheet' type='text/css'>");
		bufferedWriter.newLine();
		bufferedWriter.write("<meta charset=\"UTF-8\">");
		bufferedWriter.newLine();
		bufferedWriter.write("<myTitle>" + myTitle + "</myTitle>");
		bufferedWriter.newLine();
	}

	private void writeHeadEnd(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("</head>");
		bufferedWriter.newLine();

	}

	private void writeBodyStart(BufferedWriter bufferedWriter, String header, int dayOfToday, String monthOfToday,
			int year) throws IOException {

		bufferedWriter.write("<body id=\"body\">");
		bufferedWriter.newLine();
		bufferedWriter.write("<img src=\"");

		bufferedWriter.write(Constants.FLIGHTPLANER + "\"");
		bufferedWriter.write(" class=\"Intersportlogo\" alt = \"Intersport_Logo_blank\" align=\"left\"/>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("<h1>");
		bufferedWriter.newLine();
		bufferedWriter.write(String.format(header, dayOfWeekInGerman, dayOfToday, monthOfToday, year));
		bufferedWriter.newLine();
		bufferedWriter.write("</h1>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
	}

	private void writeBodyEnd(BufferedWriter bufferedWriter) throws IOException {

		bufferedWriter.write("</body>");
		bufferedWriter.newLine();
		bufferedWriter.write("</html>");

	}

	private void writeStyle(BufferedWriter bufferedWriter) throws IOException {

		// from top to bottom:
		bufferedWriter.write("<style>");
		bufferedWriter.newLine();

		/** html */
		bufferedWriter.write("html");
		bufferedWriter.newLine();
		bufferedWriter.write("{");
		bufferedWriter.newLine();
		bufferedWriter.write("height: 100%;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** body */
		bufferedWriter.write("body");
		bufferedWriter.newLine();
		bufferedWriter.write("{");
		bufferedWriter.newLine();
		bufferedWriter.write("min-height: 100%;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** h1 */
		bufferedWriter.write("h1 {");
		bufferedWriter.newLine();
		bufferedWriter.write("color: #001F4F;");
		bufferedWriter.newLine();
		bufferedWriter.write("clear: right;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

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
		bufferedWriter.write("#body {");
		bufferedWriter.newLine();
		bufferedWriter.write("background-size: 100% 100%;");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: center;");
		bufferedWriter.newLine();
		bufferedWriter.write(
				"background-image: url(\" " + Constants.IMAGE + Constants.Slash + Constants.BackgroundImage + "\");");

		bufferedWriter.newLine();
		bufferedWriter.write("font-family: Roboto, sans-serif;");
		bufferedWriter.newLine();
		bufferedWriter.write("background-repeat: no-repeat;");
		bufferedWriter.newLine();
		bufferedWriter.write("font-size: 20px;");
		bufferedWriter.newLine();
		bufferedWriter.write("margin: 0;");
		bufferedWriter.newLine();
		bufferedWriter.write("padding: 0;");
		bufferedWriter.newLine();
		bufferedWriter.write("min-height: 100%");
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		/** remove */

		// advertisement
		bufferedWriter.write("#advertisementId");
		bufferedWriter.newLine();
		bufferedWriter.write("{");
		bufferedWriter.newLine();
		bufferedWriter.write("color: " + Constants.WHITE + ";");
		bufferedWriter.newLine();
		bufferedWriter.write("clear: right;");
		bufferedWriter.newLine();
		bufferedWriter.write("font-size: 40px;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table wrapper */
		bufferedWriter.write("#tableWrapperId {");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table */
		bufferedWriter.write("#tableId {");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: center;");
		bufferedWriter.newLine();
		bufferedWriter.write("width:80%;");
		bufferedWriter.newLine();
		bufferedWriter.write("height:80%;");
		bufferedWriter.newLine();
		bufferedWriter.write("table-spacing: 2px;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table head */
		bufferedWriter.write(".tableheader {");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

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
		bufferedWriter.write("tr:nth-child(even) {");
		bufferedWriter.newLine();
		bufferedWriter.write("background-color:" + Constants.WHITE + ";");
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		// odd
		bufferedWriter.write("tr:nth-child(odd) {");
		bufferedWriter.newLine();
		bufferedWriter.write("background-color:" + Constants.GRAY + ";");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table data for time */
		bufferedWriter.write(".tableDataTime {");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: center;");
		bufferedWriter.newLine();
		bufferedWriter.write("width: 5%;");
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		bufferedWriter.write("border: 2px solid black;");
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table data for appointments */
		bufferedWriter.write(".tableData {");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: center;");
		bufferedWriter.newLine();
		bufferedWriter.write("width: 35%;");
		bufferedWriter.newLine();
		bufferedWriter.write("border: 2px solid black;");
		bufferedWriter.newLine();
		bufferedWriter.write("filter:alpha(opacity=90);");
		bufferedWriter.newLine();
		bufferedWriter.write("-moz-opacity: 0.90;");
		bufferedWriter.newLine();
		bufferedWriter.write("opacity: 0.90;");
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table data for appointments that should be colored */
		bufferedWriter.write(".appointmentNotEmpty {");
		bufferedWriter.newLine();
		bufferedWriter.write("background-color: " + Constants.CORAL + ";");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: left;");
		bufferedWriter.newLine();
		bufferedWriter.write("padding: 6px;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		bufferedWriter.write("</style>");
		bufferedWriter.newLine();

	}

	// public void writeTable(BufferedWriter bufferedWriter, String dayOfWeek)
	// throws IOException {
	//
	// bufferedWriter.write("<div id =\"tableWrapperId\" align=\"center\">");
	// bufferedWriter.newLine();
	// bufferedWriter.write("<table id=\"tableId\">");
	// bufferedWriter.newLine();
	// bufferedWriter.write("<thead class=\"tableheader\">");
	// bufferedWriter.write("</thead>");
	// bufferedWriter.newLine();
	// bufferedWriter.write("<tbody>");
	// bufferedWriter.newLine();
	//
	// for (int clockHour = minFullHourOfTable; clockHour <= maxFullHourOfTable;
	// clockHour++) {
	//
	// /** appointment starts at full hour */
	// bufferedWriter.write("<tr class=\"tableRow\">");
	// bufferedWriter.newLine();
	// bufferedWriter.write("<td class=\"tableDataTime\">" + clockHour + ":30" +
	// "</td>");
	// bufferedWriter.newLine();
	//
	// Appointment appointmentAtHalfHourLeftColumn =
	// writeAppointmentAtHalfHour(bufferedWriter, clockHour,
	// lstAppointmentsOfToday);
	//
	// if (appointmentAtHalfHourLeftColumn != null) {
	// writeAppointment(appointmentAtHalfHourLeftColumn, bufferedWriter);
	// } else {
	// bufferedWriter.write("<td class=\"tableData\">");
	// }
	// bufferedWriter.newLine();
	// bufferedWriter.write("</td>");
	// bufferedWriter.newLine();
	//
	// bufferedWriter.write("<td class=\"tableDataTime\">" + (clockHour + 5) + ":00"
	// + "</td>");
	// bufferedWriter.newLine();
	//
	// Appointment appointmentAtFullHourRightColumn =
	// writeAppointmentAtFullHour(bufferedWriter, (clockHour + 5),
	// lstAppointmentsOfToday);
	//
	// if (appointmentAtFullHourRightColumn != null) {
	// writeAppointment(appointmentAtFullHourRightColumn, bufferedWriter);
	// } else {
	// bufferedWriter.write("<td class=\"tableData\">");
	// }
	// bufferedWriter.newLine();
	// bufferedWriter.write("</td>");
	//
	// bufferedWriter.write("</tr>");
	// bufferedWriter.newLine();
	//
	// // ro zu ende!
	//
	// if (Integer.compare(clockHour, maxFullHourOfTable) != 0) {
	// /** appointment starts a half hour later */
	// bufferedWriter.write("<tr class=\"tableRow\">");
	// bufferedWriter.newLine();
	// int clockhourtemp = clockHour + 1;
	// bufferedWriter.write("<td class=\"tableDataTime\">" + clockhourtemp + ":00" +
	// "</td>");
	// bufferedWriter.newLine();
	// Appointment appointmentAtFullHourLeftColumn =
	// writeAppointmentAtFullHour(bufferedWriter, clockHour + 1,
	// lstAppointmentsOfToday);
	//
	// if (appointmentAtFullHourLeftColumn != null) {
	// writeAppointment(appointmentAtFullHourLeftColumn, bufferedWriter);
	// } else {
	// bufferedWriter.write("<td class=\"tableData\">");
	// }
	// }
	// bufferedWriter.newLine();
	// bufferedWriter.write("</td>");
	// bufferedWriter.newLine();
	//
	// if (Integer.compare(clockHour, maxFullHourOfTable) != 0) {
	// bufferedWriter.newLine();
	// bufferedWriter.write("<td class=\"tableDataTime\">" + (clockHour + 5) + ":30"
	// + "</td>");
	// bufferedWriter.newLine();
	// Appointment appointmentAtHalfHourRightColumn =
	// writeAppointmentAtHalfHour(bufferedWriter,
	// (clockHour + 5), lstAppointmentsOfToday);
	//
	// if (appointmentAtHalfHourRightColumn != null) {
	//
	// writeAppointment(appointmentAtHalfHourRightColumn, bufferedWriter);
	// } else {
	// bufferedWriter.write("<td class=\"tableData\">");
	// }
	// }
	// bufferedWriter.newLine();
	// bufferedWriter.write("</td>");
	// bufferedWriter.newLine();
	//
	// /** row is written */
	// bufferedWriter.write("</tr>");
	// bufferedWriter.newLine();
	//
	// }
	// bufferedWriter.write("</tbody>");
	// bufferedWriter.newLine();
	// bufferedWriter.write("</table>");
	// bufferedWriter.newLine();
	// bufferedWriter.write("</div>");
	// bufferedWriter.newLine();
	// }

	@Override
	public void write() throws IOException {

		File file = new File(
				myDirectory + File.separator + Constants.ASSETS + File.separator + Constants.FlightPlanerHtml);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}

		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

		writeHeadStart(bufferedWriter);
		writeStyle(bufferedWriter);
		writeHeadEnd(bufferedWriter);
		writeBodyStart(bufferedWriter, myHeader, dayOfToday, monthOfToday, year);
		writeBodyEnd(bufferedWriter);

		bufferedWriter.close();
	}

	//
	private void writeAdvertisement(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("<div id =\"advertisementId\" align=\"center\">");
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		// bufferedWriter.write(Constants.ADVERTISEMENT);
		bufferedWriter.write("</div>");

	}
}
