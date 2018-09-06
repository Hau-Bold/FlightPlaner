package pdf;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.io.IOException;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import gps_coordinates.GpsCoordinate;

public class PdfWrite {

	public static final String path = "C:/Users/Haubold/Desktop/document.pdf";

	public PdfWrite(List<GpsCoordinate> computedRoute) {
		createPdf(path,computedRoute);
	}


	public void createPdf(String path,List<GpsCoordinate> computedRoute)  {
		
	    PdfWriter writer = null;
		try {
			writer = new PdfWriter(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(writer != null)
		{
	    PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf );
		for(GpsCoordinate gps : computedRoute)
		{
	    document.add(new Paragraph(gps.toString()));
		}
	    document.close();
		}

	}

}
